package tr.edu.yildiz.yazilimkalite.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.MemberDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void registerNewMember_GivenMemberDto() {
        MemberDto memberDto = new MemberDto()
                                .name("Member")
                                .status(MemberStatus.ACTIVE)
                                .surname("Surname");

        Member member = memberService.saveMember(memberDto);

        assertNotNull(member);
    }

    @Test
    public void checkMemberAvaibility_GivenMember() {
        MemberDto memberDto = new MemberDto()
                                .name("Member")
                                .status(MemberStatus.ACTIVE)
                                .surname("Surname");

        Member member = memberService.saveMember(memberDto);

        assertTrue(memberService.isAvaiableToBorrowBook(member));
    }

    @Test
    public void getMemberBySearchQuery_GivenSearchQuery() {
        MemberDto memberDto = new MemberDto()
                                    .name("MemberName")
                                    .status(MemberStatus.ACTIVE)
                                    .surname("Surname");

        memberService.saveMember(memberDto);

        Page<Member> searchResult = memberService.getPaginatedByQuery("MemberName", PageRequest.of(0, 10));

        assertEquals(1L, searchResult.getTotalElements());
    }
}
