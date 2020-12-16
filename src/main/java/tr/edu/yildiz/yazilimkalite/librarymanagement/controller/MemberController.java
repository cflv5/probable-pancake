package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.MemberDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.MemberService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping({ "/", "" })
    public ModelAndView getMemberDashBoard(Model model) {
        Page<Member> pageResult = memberService.getPaginated(PageRequest.of(0, 10));
        List<MemberDto> members = memberService.convertToDto(pageResult.getContent());

        model.addAttribute(ViewConstants.FRAGMENT, "");

        model.addAttribute("members", members);
        model.addAttribute("pageResult", pageResult);

        return new ModelAndView(ViewConstants.BOILERPLATE);
    }

    @GetMapping("/{id}")
    public ModelAndView getMemberProfile(@PathVariable String id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Member member = memberService.getMemberByMemberId(id);

        if (member == null) {
            model.addAttribute(ViewConstants.FRAGMENT, "error/notfound");
            model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, "Üye bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "member/member-profile");

            Boolean isAvaiableToBorrow = memberService.isAvaiableToBorrowBook(member);
            model.addAttribute("avaiableToBorrow", isAvaiableToBorrow);
            
            model.addAttribute(ViewConstants.MEMBER, member);
        }
        return view;
    }

    @GetMapping("/add")
    public String getAddMemberForm(Model model) {
        MemberDto member = model.getAttribute(ViewConstants.MEMBER) == null ? new MemberDto()
                : (MemberDto) model.getAttribute(ViewConstants.MEMBER);

        model.addAttribute(ViewConstants.MEMBER, member);
        model.addAttribute(ViewConstants.FRAGMENT, "member/memberadd :: add-form");

        return ViewConstants.BOILERPLATE;
    }

    @PostMapping("/add")
    public RedirectView addMember(@ModelAttribute @Valid MemberDto memberDto, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            Member member = memberService.saveMember(memberDto);
            redirectAttributes.addFlashAttribute(ViewConstants.SUCCESS, true);
            redirectAttributes.addFlashAttribute(ViewConstants.SUCCESS_MESSAGE, "Üye işlemi başarıyla gerçekleşti.");
            view = new RedirectView("/members/" + member.getMemberId());
        }
        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getMemberEditForm(@PathVariable String id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Member fetchedMember = memberService.getMemberByMemberId(id);
        MemberDto member = null;

        model.addAttribute(ViewConstants.UPDATE_FORM, true);
        if (fetchedMember == null) {
            model.addAttribute(ViewConstants.FRAGMENT, "error/notfound :: callout");
            model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, "Üye bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            member = MemberDto.of(fetchedMember);
            model.addAttribute(ViewConstants.FRAGMENT, "member/memberadd :: add-form");
            model.addAttribute(ViewConstants.MEMBER, member);
        }

        return view;
    }
}
