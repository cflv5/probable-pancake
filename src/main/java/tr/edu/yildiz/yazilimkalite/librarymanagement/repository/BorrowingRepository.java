package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Borrowing;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.BorrowingStatus;

public interface BorrowingRepository extends PagingAndSortingRepository<Borrowing, String>{

	Page<Borrowing> findAllByStatus(BorrowingStatus notReturned, Pageable page);
    
}
