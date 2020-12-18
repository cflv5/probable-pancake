package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;

@Repository
public interface LibrarySettingRepository extends PagingAndSortingRepository<LibrarySetting, Long>{

	Optional<LibrarySetting> findByName(String name);
    
}
