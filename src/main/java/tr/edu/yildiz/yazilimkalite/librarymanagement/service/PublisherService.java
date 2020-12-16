package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.PublisherDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.PublisherRepository;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(PublisherDto publisherDto) {
        Publisher publisher = null;

        if (publisherDto.getId() != null) {
            Optional<Publisher> fetchedPublisher = publisherRepository.findById(publisherDto.getId());
            if (fetchedPublisher.isPresent()) {
                publisher = fetchedPublisher.get();
                BeanUtils.copyProperties(publisherDto, publisher, "id");
                publisherRepository.save(publisher);
            } else {
                throw new EntityNotFoundException("Publisher with supplied id do not exist");
            }
        } else {
            publisher = publisherRepository.save(Publisher.of(publisherDto));
        }

        return publisher;
    }

	public Publisher getPublisherById(Long id) {
        Optional<Publisher> fetchedPublisher = publisherRepository.findById(id);
        Publisher publisher = null;

        if (fetchedPublisher.isPresent()) {
            publisher = fetchedPublisher.get();
        }

        return publisher;
	}

	public Page<Publisher> getPaginated(Pageable page) {
		return publisherRepository.findAll(page);
	}

	public List<Publisher> getBySearchQuery(String query, Pageable page) {
		return publisherRepository.findAllBySearchQuery(query, page);
	}

}
