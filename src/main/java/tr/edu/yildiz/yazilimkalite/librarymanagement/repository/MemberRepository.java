package tr.edu.yildiz.yazilimkalite.librarymanagement.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

	Optional<Member> findByMemberId(String id);

	@Query("SELECT m FROM Member m " +
			"WHERE (m.status = :status) AND " +
			"(lower(m.name) LIKE :query% OR lower(m.surname) LIKE :query% " + 
			"OR m.memberId LIKE :query%)")
	Page<Member> findByQueryAndStatus(String query, MemberStatus status, Pageable page);

}
