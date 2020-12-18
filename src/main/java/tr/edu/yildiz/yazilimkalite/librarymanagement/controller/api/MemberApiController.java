package tr.edu.yildiz.yazilimkalite.librarymanagement.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.MemberDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.response.Response;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<Response<List<MemberDto>>> getMember(@RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "ACTIVE") MemberStatus status, @RequestParam(defaultValue = "5") int size) {
        List<Member> members = memberService.getByStatusAndSearchQuery(query, status, PageRequest.of(0, size)).getContent();
        
        if (members.isEmpty()) {
            return new ResponseEntity<>(new Response<>(false, null, "Member could not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>(true, memberService.convertToDto(members), "Member found."), HttpStatus.OK);
    }
}
