package tr.edu.yildiz.yazilimkalite.librarymanagement.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.response.Response;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookApiController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Response<List<Book>>> getBooks(@RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "false") boolean borrowed, @RequestParam(defaultValue = "5") int size) {
        List<Book> books = bookService.getBookBySearchQuery(query, borrowed, size);

        if (books.isEmpty()) {
            return new ResponseEntity<>(new Response<>(false, null, "Aranan kriterde kitap bulunamadı"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>(true, books, books.size() + " adet kitap bulundu."),
                HttpStatus.OK);
    }

    @GetMapping("/statistics/count")
    public ResponseEntity<Response<List<StatisticResultMapping>>> getBookCountStatistics(
            @RequestParam(required = false) String by) {
        return new ResponseEntity<>(new Response<>(true, bookService.getBookCountByBorrowed(), "booksfound"),
                HttpStatus.OK);
    }
}
