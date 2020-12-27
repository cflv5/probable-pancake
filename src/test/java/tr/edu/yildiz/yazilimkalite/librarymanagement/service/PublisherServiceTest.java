package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.PublisherDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;

@SpringBootTest
@ActiveProfiles("test")
public class PublisherServiceTest {
    @Autowired
    private PublisherService publisherService;

    @Test
    public void saveNewPublisher_GivenPublisherDto() {
        PublisherDto publisherDto = new PublisherDto().name("Name");

        Publisher publisher = publisherService.savePublisher(publisherDto);

        assertNotNull(publisher);

        assertEquals(publisherDto.getName(), publisher.getName());
    }

    @Test
    public void getPublisher_GivenPublisherId() {
        PublisherDto publisherDto = new PublisherDto().name("Name");

        Publisher publisher = publisherService.savePublisher(publisherDto);

        assertNotNull(publisher);

        assertNotNull(publisherService.getPublisherById(publisher.getId()));
    }
}
