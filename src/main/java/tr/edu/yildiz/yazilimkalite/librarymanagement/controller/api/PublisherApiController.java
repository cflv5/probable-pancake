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
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherApiController {
    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<Response<List<Publisher>>> getPublishers(@RequestParam(defaultValue = "") String query) {
        List<Publisher> publishers = publisherService.getBySearchQuery(query, PageRequest.of(0, 5));

        if (publishers.isEmpty()) {
            return new ResponseEntity<>(new Response<>(false, null, "İstenilen kriterlerde yayımcı bulunamadı."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>(true, publishers, "İstenilen kriterlerde yayımcı bulunamadı."),
                HttpStatus.OK);

    }
}
