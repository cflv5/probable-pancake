package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BorrowingRecordingDto;
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

@Service
public class BorrowingService {
	@Autowired
	private BorrowingRepository borrowingRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BookRepository bookRepository;

	public Borrowing saveBorrowing(BorrowingRecordingDto borrowingRecord) {
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

		List<Book> books = bookRepository.findAllByIdIn(borrowingRecord.getBooks());

		if (books.size() != borrowingRecord.getBooks().size()) {
			throw new NotExistingEntityException("At least one of the entered books does not exist.");
		}

		books.forEach(book -> {
			if(book.isBorrowed() != null && book.isBorrowed()) {
				throw new BookAlreadyBorrowedException("Id with " + book.getId() + " is already borrowed.", book.getName());
			}
			book.setBorrowed(true);
		});

		borrowing.setBooks(books);

		Date startDate = borrowingRecord.getStartDate();
		Date deadline = borrowingRecord.getDeadline();

		compareAndCheckStartDate(startDate);
		borrowing.setStartDate(startDate);

		if (!compareAndCheckDeadline(deadline)) {
			throw new NotAcceptableDeadlineException("Specified deadline is not acceptable.");
		}

		borrowing.setDeadline(deadline);

		borrowing.setCreator(new User().id(1L));
		borrowing.setStatus(BorrowingStatus.NOT_RETURNED);
		borrowing.setExtension(0);

		borrowingRepository.save(borrowing);

		return borrowing;
	}

	public Page<Borrowing> getPaginatedBorrowingsByStatusNotReturned(Pageable page) {
		return borrowingRepository.findAllByStatus(BorrowingStatus.NOT_RETURNED, page);
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
		return borrowing.getExtension() <= 3;
	}

	private void compareAndCheckStartDate(Date startDate) {
		LocalDate today = new Date(Calendar.getInstance().getTime().getTime()).toLocalDate();
		LocalDate startDateLocal = startDate.toLocalDate();

		if (startDateLocal.isBefore(today)) {
			throw new RetroactiveRecordException("Retroactive record is not allowed.");
		} else if (startDateLocal.isAfter(today)) {
			throw new ForwardRecordException("Forward record is not allowed.");
		}
	}

	private boolean compareAndCheckDeadline(Date deadline) {
		boolean isAccepted = false;

		Calendar calendar = Calendar.getInstance();

		Date today = new Date(calendar.getTime().getTime());
		if (deadline.toLocalDate().compareTo(today.toLocalDate()) > 0) {

			calendar.add(Calendar.DAY_OF_YEAR, 21);
			Date acceptableDeadline = new Date(calendar.getTime().getTime());
			if (deadline.toLocalDate().compareTo(acceptableDeadline.toLocalDate()) < 0) {
				isAccepted = true;
			}
		}

		return isAccepted;
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

		if (borrowing.getExtension() > 3) {
			throw new NoMoreExtensionAllowedException("Member exceeded the extension limit.");
		}

		if (isBorrowingLate(borrowing)) {
			throw new RetroactiveRecordException("Cannot extend late records.");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(borrowing.getDeadline());
		calendar.add(Calendar.DAY_OF_YEAR, 14);

		borrowing.setDeadline(new Date(calendar.getTime().getTime()));

		borrowing.setExtension(borrowing.getExtension() + 1);

		borrowingRepository.save(borrowing);

		return borrowing;
	}

}