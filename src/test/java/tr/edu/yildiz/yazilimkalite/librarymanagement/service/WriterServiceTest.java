package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.WriterDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;

@SpringBootTest
@ActiveProfiles("test")
public class WriterServiceTest {
    
    @Autowired
    private WriterService writerService;

    @Test
    public void saveNewWriter_GivenWriterDto() {
        WriterDto writerDto = new WriterDto().name("Name").surname("Surname");

        Writer writer = writerService.save(writerDto);
    
        assertNotNull(writer);

        assertArrayEquals(new Object[] {
            writerDto.getName(),
            writerDto.getSurname()
        }, new Object[] {
            writer.getName(),
            writer.getSurname()
        });
    }

    @Test
    public void getWriterById() {
        WriterDto writerDto = new WriterDto().name("Name").surname("Surname");

        Writer writer = writerService.save(writerDto);
        assertNotNull(writer);

        assertNotNull(writerService.getWriterById(writer.getId()));
    }

    @Test
    public void getWriterBySearchQuery_GivenSearchQuery() {
        WriterDto writerDto = new WriterDto().name("writername").surname("Surname");

        Writer writer = writerService.save(writerDto);
        assertNotNull(writer);

        Page<Writer> searchResult = writerService.getBySearchQuery("writername", PageRequest.of(0, 10));

        assertEquals(1, searchResult.getTotalElements());
    }
}
