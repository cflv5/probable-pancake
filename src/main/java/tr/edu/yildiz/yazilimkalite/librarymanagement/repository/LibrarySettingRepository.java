package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;

@Repository
public interface LibrarySettingRepository extends PagingAndSortingRepository<LibrarySetting, Long>{
    
}
