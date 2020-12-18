package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BookRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private WriterService writerService;

    public Book getBookByIsbn(String isbn) {
        Book book = null;
        Optional<Book> fetchedBook = bookRepository.findByIsbn(isbn);

        if (fetchedBook.isPresent()) {
            book = fetchedBook.get();
        }

        return book;
    }

    public Book getBookById(Long id) {
        Book book = null;
        Optional<Book> fetchedBook = bookRepository.findById(id);

        if (fetchedBook.isPresent()) {
            book = fetchedBook.get();
        }

        return book;
    }

    public Page<Book> getPaginatedAndSortedByName(Pageable page) {
        return bookRepository.findAll(page);
    }

    public boolean isAvailableToBorrow(Book book) {
        return !book.isBorrowed();
    }

    public Book save(BookRegistrationDto bookDto) {
        Book book = null;

        if (bookDto.getId() != null) {
            book = getBookById(bookDto.getId());

            if (book == null) {
                throw new NotExistingEntityException("Book with id " + bookDto.getId() + " was not found.", "book");
            }
        } else {
            book = new Book();
        }

        BeanUtils.copyProperties(bookDto, book, "id", "publisher", "writers", "borrowed");

        setPublisher(book, bookDto.getPublisher());
        setWriters(book, bookDto.getWriters());

        book.setBorrowed(false);

        bookRepository.save(book);

        return book;
    }

    private void setWriters(Book book, List<Long> writerIds) {
        if (writerIds == null || writerIds.isEmpty()) {
            book.setWriters(null);
        } else {
            List<Writer> writers = writerService.getAllByIds(writerIds);

            if (writerIds.size() != writers.size()) {
                throw new NotExistingEntityException("At least one of the writes specified do not exists.", "writer");
            }

            book.setWriters(writers);
        }
    }

    private void setPublisher(Book book, Long publisherId) {
        if (publisherId == null) {
            book.setPublisher(null);
        } else {
            Publisher publisher = publisherService.getPublisherById(publisherId);

            if (publisher == null) {
                throw new NotExistingEntityException("Publisher with " + publisherId + " was not found.", "publisher");
            }

            book.setPublisher(publisher);
        }
    }

    public Page<Book> getBySearchQueryPaginated(String query, Pageable page) {
        return bookRepository.findAllBySearchQuery(query.toLowerCase(), page);
    }

    public List<Book> getBookBySearchQuery(String query, Boolean borrowed, int size) {
        return bookRepository.findAllBySearchQueryAndBorrowed(query.toLowerCase(), borrowed, PageRequest.of(0, size)).getContent();
    }

}
