package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.MemberDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;
import tr.edu.yildiz.yazilimkalite.librarymanagement.repository.MemberRepository;

@Service
public class MemberService {
    @Value("${library.member.id.length}")
    private Integer libraryIdLenght;

    @Autowired
    private MemberRepository memberRepository;

    public Page<Member> getPaginatedByQuery(String query, Pageable page) {
        return memberRepository.findByQuery(query.toLowerCase(), page);
    }
    public List<MemberDto> convertToDto(List<Member> members) {
        return members.stream().map(MemberDto::of).collect(Collectors.toList());
    }

    public Member getMemberByMemberId(String id) {
        Optional<Member> fetchedMember = memberRepository.findByMemberId(id);
        Member member = null;

        if (fetchedMember.isPresent()) {
            member = fetchedMember.get();
        }

        return member;
    }

    public Member saveMember(MemberDto memberDto) {
        Member member = null;

        if (memberDto.getMemberId() != null) {
            member = getMemberByMemberId(memberDto.getMemberId());
            if (member != null) {
                BeanUtils.copyProperties(memberDto, member, "id", "libraryId", "borrowings");
                memberRepository.save(member);
            } else {
                throw new EntityNotFoundException("Publisher with supplied id do not exist");
            }
        } else {
            member = Member.of(memberDto);
            member.setMemberId(getUniqueLibraryId(libraryIdLenght));
            memberRepository.save(member);
        }

        return member;
    }

    public Page<Member> getByStatusAndSearchQuery(String query, MemberStatus status, Pageable page) {
        return memberRepository.findByQueryAndStatus(query, status, page);
    }

    private String getUniqueLibraryId(int len) {
        String librayId = "";
        do {
            librayId = RandomStringUtils.randomAlphanumeric(len);
        } while (getMemberByMemberId(librayId) != null);
        return librayId;
    }

	public Boolean isAvaiableToBorrowBook(Member member) {
		return member.getStatus().equals(MemberStatus.ACTIVE);
	}
}
