package tr.edu.yildiz.yazilimkalite.librarymanagement.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.response.Response;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.WriterService;

@RestController
@RequestMapping("/api/writers")
public class WriterApiController {
    @Autowired
    private WriterService writerService;

    @GetMapping
    public ResponseEntity<Response<List<Writer>>> getWriters(@RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "5") int size) {
        List<Writer> writers = writerService.getBySearchQuery(query, PageRequest.of(0, size)).getContent();

        if (writers.isEmpty()) {
            return new ResponseEntity<>(new Response<>(false, null, "Aranan kriterde yazar bulunamadı."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>(true, writers, writers.size() + " yazar bulundu."), HttpStatus.OK);
    }

}
