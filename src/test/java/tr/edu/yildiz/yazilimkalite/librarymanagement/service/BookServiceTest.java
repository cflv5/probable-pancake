package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BookRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void dependenciesWired() {
        assertNotNull(bookService);
    }

    @Test
    public void saveNewBook_GivenBookRegistrationDto() {
        BookRegistrationDto bookRegistrationDto = new BookRegistrationDto()
                                                        .isbn("isbn")
                                                        .name("name");

        Book book = bookService.save(bookRegistrationDto);

        assertNotNull(book);
    }

    @Test
    public void chechBookAvailibity_GivenBookId() {
        BookRegistrationDto bookRegistrationDto = new BookRegistrationDto()
                                                        .isbn("isbn")
                                                        .name("name");

        Book book = bookService.save(bookRegistrationDto);

        assertNotNull(book);

        assertTrue(bookService.isAvailableToBorrow(book));
    }
}
