package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u " + 
            "WHERE " + 
            "lower(u.name) LIKE :query% OR " + 
            "lower(u.surname) LIKE :query% OR " + 
            "lower(u.email) LIKE :query%"
        )
    Page<User> findAllBySearchQuery(String query, Pageable page);

    @Query("SELECT " +
            "new tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping(u.status, COUNT(u.status)) " +
            "FROM User u " +
            "GROUP BY u.status")
    List<StatisticResultMapping> countGroupByStatus();
}
