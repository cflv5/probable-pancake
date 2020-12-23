package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BorrowingRecordingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.BookAlreadyBorrowedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.BorrowingAlreadyReturnedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ForwardRecordException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ImproperMemberStatusException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.MemberNotExistException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NoMoreExtensionAllowedException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotAcceptableDeadlineException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.RetroactiveRecordException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Borrowing;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.BorrowingStatus;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.PancakeUserDetails;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.BookService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.BorrowingService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.MemberService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ModelAttributeHelper;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookService bookService;

    private static final String BORROWING_DETAILS = "borrowing/details";

    private static final String BORROWINGS_PATH = "/borrowings/";
    private static final String BORROWING_CODE_NOT_FOUND_MESSAGE = " kodlu kayıt bulunamadı.";

    @GetMapping({ "", "/" })
    public ModelAndView showBorrowingsDashbord(@RequestParam(required = false, defaultValue = "") String query,
            @PageableDefault Pageable page, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        String url = "/borrowings?";
        if (!query.isBlank()) {
            url = "/borrowings?query=" + query + "&";
        }

        Page<Borrowing> borrowings = borrowingService.getPaginatedBorrowingsByQuery(query, page);
        model.addAttribute("page", borrowings);
        model.addAttribute("url", url);
        model.addAttribute(ViewConstants.FRAGMENT, "borrowing/borrowing-index");
        return view;
    }

    @GetMapping("/{borrowingId}")
    public ModelAndView showBorrowingDetails(@PathVariable String borrowingId, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);
        Borrowing borrowing = borrowingService.getBorrowingById(borrowingId);

        if (borrowing == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model,
                    borrowingId + BORROWING_CODE_NOT_FOUND_MESSAGE);
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.BORROWING, borrowing);
            if (borrowing.getStatus().equals(BorrowingStatus.LOST)
                    || borrowing.getStatus().equals(BorrowingStatus.NOT_RETURNED)) {

                if (borrowingService.isBorrowingLate(borrowing)) {
                    model.addAttribute("late", true);
                }

                if (!borrowingService.isAvailableToExtend(borrowing)) {
                    model.addAttribute("noExtend", true);
                }

            }
            model.addAttribute(ViewConstants.FRAGMENT, BORROWING_DETAILS);
        }

        return view;
    }

    @GetMapping("/{borrowingId}/refund")
    public RedirectView refundBorrowing(@PathVariable String borrowingId, RedirectAttributes model) {
        RedirectView view = new RedirectView(BORROWINGS_PATH + borrowingId);
        Borrowing borrowing = borrowingService.getBorrowingById(borrowingId);

        if (borrowing == null) {
            ModelAttributeHelper.addFlashAttrNotFoundFragmentAndErrorMessage(model,
                    borrowingId + BORROWING_CODE_NOT_FOUND_MESSAGE);
            view.setStatusCode(HttpStatus.NOT_FOUND);
        } else {
            try {
                borrowingService.refund(borrowing);
                model.addFlashAttribute(ViewConstants.SUCCESS, true);
                model.addFlashAttribute(ViewConstants.SUCCESS_MESSAGE, "İade işlemi başarılı.");
            } catch (BorrowingAlreadyReturnedException e) {
                model.addFlashAttribute("alreadyReturned", true);
            }
        }
        return view;
    }

    @GetMapping("/{borrowingId}/extend")
    public RedirectView extendDeadline(@PathVariable String borrowingId, RedirectAttributes model) {
        RedirectView view = new RedirectView(BORROWINGS_PATH + borrowingId);
        Borrowing borrowing = borrowingService.getBorrowingById(borrowingId);

        if (borrowing == null) {
            ModelAttributeHelper.addFlashAttrNotFoundFragmentAndErrorMessage(model,
                    borrowingId + BORROWING_CODE_NOT_FOUND_MESSAGE);
            view.setStatusCode(HttpStatus.NOT_FOUND);
        } else {
            try {
                borrowingService.extend(borrowing);
                model.addFlashAttribute(ViewConstants.SUCCESS, true);
                model.addFlashAttribute(ViewConstants.SUCCESS_MESSAGE, "İade süresi uzatma işlemi başarılı.");
            } catch (BorrowingAlreadyReturnedException e) {
                model.addFlashAttribute("alreadyReturned", true);
            } catch (NoMoreExtensionAllowedException e) {
                model.addFlashAttribute("noExtensionRightLeft", true);
            } catch (RetroactiveRecordException e) {
                model.addFlashAttribute("noRetroactiveExtention", true);
            }
        }

        return view;
    }

    @GetMapping("/create")
    public ModelAndView getCreateBorrowingRecordForm(@RequestParam(required = false) String memberId,
            @RequestParam(required = false) Long bookId, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        model.addAttribute(ViewConstants.BORROWING, new BorrowingRecordingDto());
        model.addAttribute(ViewConstants.FRAGMENT, "borrowing/create-borrowing :: create-form");
        model.addAttribute("js", Arrays.asList("/js/borrowing.js"));

        if (memberId != null && !memberId.isBlank()) {
            Member member = memberService.getMemberByMemberId(memberId);
            if (member != null) {
                model.addAttribute(ViewConstants.MEMBER, member);
            }
        }

        if (bookId != null) {
            Book book = bookService.getBookById(bookId);
            if (book != null) {
                model.addAttribute(ViewConstants.BOOK, book);
            }
        }
        return view;
    }

    @PostMapping("/create")
    public RedirectView createBorrowingRecord(@ModelAttribute @Valid BorrowingRecordingDto borrowingDto,
            BindingResult result, RedirectAttributes model, @AuthenticationPrincipal PancakeUserDetails user) {
        RedirectView view = null;
        final String BORROWING_CREATE_URI = "/borrowings/create";
        final String MEMBER_ID = "memberId";

        if (result.hasErrors()) {
            model.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            return new RedirectView(BORROWING_CREATE_URI);
        }
        try {
            Borrowing borrowing = borrowingService.saveBorrowing(borrowingDto, user.getUser());

            model.addFlashAttribute(ViewConstants.SUCCESS, true);
            model.addFlashAttribute(ViewConstants.SUCCESS_MESSAGE, "Ödünç verme işlemi başarılı.");
            view = new RedirectView(BORROWINGS_PATH + borrowing.getId());
        } catch (MemberNotExistException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model, "Üye bulunamadı.");
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (ImproperMemberStatusException e) {
            model.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            model.addFlashAttribute(ViewConstants.Error.INTEGRITY_ERROR, true);
            model.addFlashAttribute(ViewConstants.Error.MEMBER_STATUS_ERROR, true);
            model.addFlashAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (NotExistingEntityException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model,
                    "Ödünç alınmak istenen kitaplardan en az biri kayıtlı değil");
            model.addAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (BookAlreadyBorrowedException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model,
                    e.getBookName() + " zaten ödünç verilmiş.");
            model.addAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (ForwardRecordException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model,
                    "İleriye dönük ödünç kaydı yapılamıyor.");
            model.addAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (RetroactiveRecordException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model,
                    "Geriye dönük ödünç kaydı yapılamıyor.");
            model.addAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        } catch (NotAcceptableDeadlineException e) {
            ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(model,
                    "İade tarihi gerçerli aralıkta değil.");
            model.addAttribute(MEMBER_ID, borrowingDto.getMember());
            view = new RedirectView(BORROWING_CREATE_URI);
        }
        return view;
    }

}
