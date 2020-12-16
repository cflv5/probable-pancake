package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.WriterDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.WriterRepository;

@Service
public class WriterService {
    @Autowired
    private WriterRepository writerRepository;

    public Writer save(WriterDto writerDto) {
        Writer writer = null;

        if(writerDto.getId() != null) {
            writer = getWriterById(writerDto.getId());
            if (writer != null) {
                writer.name(writerDto.getName()).surname(writerDto.getSurname());
                writerRepository.save(writer);
            } else {
                throw new NotExistingEntityException("Writer with id" + writerDto.getId() + " does not exist.");
            }
        } else {
            writer = writerRepository.save(Writer.of(writerDto));
        }

        return writer;
    }

    public Writer getWriterById(Long writerId) {
        Optional<Writer> fetchedWriter = writerRepository.findById(writerId);
        Writer writer = null;

        if (fetchedWriter.isPresent()) {
            writer = fetchedWriter.get();
        }

        return writer;
    }

    public Page<Writer> getPaginated(Pageable page) {
        return writerRepository.findAll(page);
    }

	public List<Writer> getAllByIds(List<Long> writerIds) {
        List<Writer> writers = new ArrayList<>();
        writerRepository.findAllById(writerIds).forEach(writers::add);
        return writers;
	}

	public Page<Writer> getBySearchQuery(String query, Pageable page) {
		return writerRepository.findAllBySearchQuery(query, page);
    }

	public List<WriterDto> convertToDto(List<Writer> writers) {
		return writers.stream().map(WriterDto::of).collect(Collectors.toList());
	}
    
}
