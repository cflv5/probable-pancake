package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long>{
	List<UserRole> findAllByIdIn(List<Long> roles);
}
