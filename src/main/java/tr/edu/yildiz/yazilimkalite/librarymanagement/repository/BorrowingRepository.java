package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Borrowing;

public interface BorrowingRepository extends PagingAndSortingRepository<Borrowing, String>{

	@Query("SELECT b FROM Borrowing b " +
			"INNER JOIN Member m ON m.id = b.member " +
			"WHERE lower(m.name) LIKE :query% OR " +
			"lower(m.surname) LIKE :query% OR " +
			"b.id LIKE :query% OR m.memberId LIKE :query% " )
	Page<Borrowing> findAllByQuery(String query, Pageable page);

	@Query("SELECT " +
		"new tr.edu.yildiz.yazilimkalite.librarymanagement.dto.mapping.StatisticResultMapping(b.status, COUNT(b.status)) " +
		"FROM Borrowing b " +
		"GROUP BY b.status")
	List<StatisticResultMapping> countGroupByStatus();
}
