package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BorrowingRecordingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.AllowedSizeExceededException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.BookAlreadyBorrowedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.BorrowingAlreadyReturnedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ForwardRecordException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ImproperMemberStatusException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.MemberNotExistException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NoMoreExtensionAllowedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotAcceptableDeadlineException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.RetroactiveRecordException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Borrowing;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.BorrowingStatus;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.BookRepository;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.BorrowingRepository;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.LibrarySettingNames;

@Service
public class BorrowingService {
	@Autowired
	private BorrowingRepository borrowingRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LibrarySettingService librarySettingService;

	public Borrowing saveBorrowing(BorrowingRecordingDto borrowingRecord, User user) {
		Borrowing borrowing = new Borrowing();

		Member member = memberService.getMemberByMemberId(borrowingRecord.getMember());

		if (member == null) {
			throw new MemberNotExistException(
					"Library member with id: " + borrowingRecord.getMember() + " does not exist.");
		}

		if (!member.getStatus().equals(MemberStatus.ACTIVE)) {
			throw new ImproperMemberStatusException("Member(" + member.getMemberId() + ") has improper status("
					+ member.getStatus() + ") to borrow book.");
		}
		borrowing.setMember(member);

		String allowedSize = librarySettingService.getByName(LibrarySettingNames.ALLOW_BORROWING_BOOKS_SIZE).getValue();

		if (borrowingRecord.getBooks().size() > Integer.parseInt(allowedSize)) {
			throw new AllowedSizeExceededException("Allowed size for books in a borrowing exceeded.", allowedSize);
		}

		List<Book> books = bookRepository.findAllByIdIn(borrowingRecord.getBooks());

		if (books.size() != borrowingRecord.getBooks().size()) {
			throw new NotExistingEntityException("At least one of the entered books does not exist.");
		}

		books.forEach(book -> {
			if (book.isBorrowed() != null && book.isBorrowed()) {
				throw new BookAlreadyBorrowedException("Id with " + book.getId() + " is already borrowed.",
						book.getName());
			}
			book.setBorrowed(true);
		});

		borrowing.setBooks(books);

		Date startDate = borrowingRecord.getStartDate();
		Date deadline = borrowingRecord.getDeadline();

		compareAndCheckStartDate(startDate);
		borrowing.setStartDate(startDate);

		compareAndCheckDeadline(deadline, startDate);
		borrowing.setDeadline(deadline);

		borrowing.setCreator(user);
		borrowing.setStatus(BorrowingStatus.NOT_RETURNED);
		borrowing.setExtension(0);

		borrowingRepository.save(borrowing);

		return borrowing;
	}

	public Page<Borrowing> getPaginatedBorrowingsByQuery(String query, Pageable page) {
		return borrowingRepository.findAllByQuery(query, page);
	}

	public boolean isBorrowingLate(Borrowing borrowing) {
		return borrowing.getDeadline().toLocalDate()
				.isBefore(new Date(Calendar.getInstance().getTime().getTime()).toLocalDate());
	}

	public Borrowing getBorrowingById(String borrowingId) {
		Optional<Borrowing> fetchedBorrowing = borrowingRepository.findById(borrowingId);
		Borrowing borrowing = null;

		if (fetchedBorrowing.isPresent()) {
			borrowing = fetchedBorrowing.get();
		}

		return borrowing;
	}

	public boolean isAvailableToExtend(Borrowing borrowing) {
		return borrowing.getExtension() < Integer
				.parseInt(librarySettingService.getByName(LibrarySettingNames.MAX_EXTENSION).getValue());
	}

	private void compareAndCheckStartDate(Date startDate) {
		LocalDate today = new Date(Calendar.getInstance().getTime().getTime()).toLocalDate();
		LocalDate startDateLocal = startDate.toLocalDate();

		boolean retroactiveBorrow = Boolean
				.parseBoolean(librarySettingService.getByName(LibrarySettingNames.RETROACTIVE_BORROW).getValue());
		boolean forwardBorrow = Boolean
				.parseBoolean(librarySettingService.getByName(LibrarySettingNames.FORWARD_BORROW).getValue());

		if (!retroactiveBorrow && startDateLocal.isBefore(today)) {
			throw new RetroactiveRecordException("Retroactive record is not allowed.");
		} else if (!forwardBorrow && startDateLocal.isAfter(today)) {
			throw new ForwardRecordException("Forward record is not allowed.");
		}
	}

	private void compareAndCheckDeadline(Date deadline, Date startDate) {
		boolean isAccepted = false;

		int minBorrowDay = Integer
				.parseInt(librarySettingService.getByName(LibrarySettingNames.MIN_BORROW_DAY).getValue());
		int maxBorrowDay = Integer
				.parseInt(librarySettingService.getByName(LibrarySettingNames.MAX_BORROW_DAY).getValue());

		if (DateUtils.truncatedCompareTo(deadline, startDate, Calendar.DATE) > 0) {
			Date minBorrowDate = new Date(DateUtils.addDays(startDate, minBorrowDay).getTime());
			Date maxBorrowDate = new Date(DateUtils.addDays(startDate, maxBorrowDay).getTime());

			if ((DateUtils.truncatedCompareTo(minBorrowDate, deadline, Calendar.DATE) <= 0)
					&& (DateUtils.truncatedCompareTo(maxBorrowDate, deadline, Calendar.DATE) >= 0)) {
				isAccepted = true;
			}

		}

		if (!isAccepted) {
			throw new NotAcceptableDeadlineException("Specified deadline is not acceptable.");
		}
	}

	public Borrowing refund(Borrowing borrowing) {
		if (borrowing.getStatus().equals(BorrowingStatus.LATE)
				|| borrowing.getStatus().equals(BorrowingStatus.RETURNED)) {
			throw new BorrowingAlreadyReturnedException("Borrowing already returned.");
		}

		borrowing.setRefundDate(new Date(Calendar.getInstance().getTime().getTime()));

		borrowing.setStatus(BorrowingStatus.RETURNED);
		if (isBorrowingLate(borrowing)) {
			borrowing.setStatus(BorrowingStatus.LATE);
		}

		borrowing.getBooks().forEach(book -> book.setBorrowed(false));

		borrowingRepository.save(borrowing);
		
		return borrowing;
	}

	public Borrowing extend(Borrowing borrowing) {
		if (borrowing.getStatus().equals(BorrowingStatus.LATE)
				|| borrowing.getStatus().equals(BorrowingStatus.RETURNED)) {
			throw new BorrowingAlreadyReturnedException("Borrowing already returned.");
		}

		int maxExtension = Integer
				.parseInt(librarySettingService.getByName(LibrarySettingNames.MAX_EXTENSION).getValue());

		if (borrowing.getExtension() >= maxExtension) {
			throw new NoMoreExtensionAllowedException("Member exceeded the extension limit.");
		}

		if (isBorrowingLate(borrowing)) {
			throw new RetroactiveRecordException("Cannot extend late records.");
		}

		int extensionDay = Integer
				.parseInt(librarySettingService.getByName(LibrarySettingNames.EXTENSION_DAY).getValue());

		Date newDeadline = new Date(DateUtils.addDays(borrowing.getDeadline(), extensionDay).getTime());
		borrowing.setDeadline(newDeadline);

		borrowing.setExtension(borrowing.getExtension() + 1);

		borrowingRepository.save(borrowing);

		return borrowing;
	}

	public List<StatisticResultMapping> getBorrowingCountByStatus() {
		return borrowingRepository.countGroupByStatus();
	}

}