package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<Publisher, Long>{

    @Query("SELECT p FROM Publisher p " + 
            "WHERE lower(p.name) LIKE CONCAT(:query, '%')")
	List<Publisher> findAllBySearchQuery(String query, Pageable page);
    
}
