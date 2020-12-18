package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

	List<Book> findAllByIdIn(List<Long> ids);

    @Query(value = "SELECT b FROM Book b " + 
                    "WHERE (b.borrowed = :borrowed) AND " +
                    "(lower(b.name) LIKE CONCAT(:query, '%') OR lower(b.isbn) = :query)"
                )
    Page<Book> findAllBySearchQueryAndBorrowed(String query, Boolean borrowed, Pageable page);
    
    @Query(value = "SELECT b FROM Book b " + 
                    "WHERE " +
                    "(lower(b.name) LIKE CONCAT(:query, '%') OR lower(b.isbn) = :query)"
                )
	Page<Book> findAllBySearchQuery(String query, Pageable page);

    @Query("SELECT " +
            "new tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping(b.borrowed, COUNT(b.borrowed)) " +
            "FROM Book b " +
            "GROUP BY b.borrowed")
	List<StatisticResultMapping> countGroupByBorrowed();
}
