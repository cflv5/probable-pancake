package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u " + 
            "WHERE lower(u.name) = :query OR " + 
            "lower(u.surname) = :query OR " + 
            "lower(u.email) LIKE :query%"
        )
    Page<User> findAllBySearchQuery(String query, Pageable page);
}
