package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;

@Repository
public interface WriterRepository extends PagingAndSortingRepository<Writer, Long> {

    @Query("SELECT w FROM Writer w " +
            "WHERE (lower(w.name) LIKE CONCAT(:query, '%')) OR (lower(w.surname) LIKE CONCAT(:query, '%'))")
	Page<Writer> findAllBySearchQuery(String query, Pageable page);
    
}
