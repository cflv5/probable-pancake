package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.BookRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Book;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.BookService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ModelAttributeHelper;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookService bookService;

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping({ "", "/" })
    public ModelAndView showBooksDashboard(@RequestParam(required = false, defaultValue = "") String query,
            @PageableDefault Pageable page, Model model) {
        Page<Book> books = null;
        String url = "/books?";
        if (!query.isBlank()) {
            url = "/books?query=" + query + "&";
        }
        books = bookService.getBySearchQueryPaginated(query, page);

        model.addAttribute("page", books);
        model.addAttribute("url", url);
        model.addAttribute(ViewConstants.FRAGMENT, "book/book-index");

        return new ModelAndView(ViewConstants.BOILERPLATE);
    }

    @GetMapping("/{id}")
    public ModelAndView showBookDetail(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);
        Book book = bookService.getBookById(id);

        if (book == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model, id + " numaralı kitap bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute("book", book);

            if (!bookService.isAvailableToBorrow(book)) {
                model.addAttribute("notAvailableToBorrow", true);
            }

            model.addAttribute(ViewConstants.FRAGMENT, "book/detail");
        }
        return view;
    }

    @GetMapping("/add")
    public ModelAndView getAddBookForm(Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        if (model.getAttribute(ViewConstants.BOOK) == null) {
            model.addAttribute(ViewConstants.BOOK, new BookRegistrationDto());
        }

        model.addAttribute("js", Arrays.asList("/js/add-book.js"));
        model.addAttribute(ViewConstants.FRAGMENT, "book/add-form");

        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditBookForm(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Book book = bookService.getBookById(id);

        if(book == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model, id + " kimlik numaralı kitap bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute("js", Arrays.asList("/js/add-book.js"));
            model.addAttribute(ViewConstants.BOOK, book);
            model.addAttribute(ViewConstants.UPDATE_FORM, book);
            model.addAttribute(ViewConstants.FRAGMENT, "book/add-form");
        }

        return view;
    }

    @PostMapping("/add")
    public RedirectView addBook(@ModelAttribute @Valid BookRegistrationDto bookDto, BindingResult result,
            RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            try {
                Book book = bookService.save(bookDto);
                redirectAttributes.addFlashAttribute("success", true);
                view = new RedirectView("/books/" + book.getId());
            } catch (NotExistingEntityException e) {
                logger.info(e.getLocalizedMessage());
                switch (e.getEntityName()) {
                    case "book":
                        ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                                "Kitap bulunamadı.");
                        break;
                    case "publisher":
                        ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                                "Yayımcı bulunamadı.");
                        break;
                    case "writer":
                        ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                                "Yazarlardan en az biri bulunamadı.");
                        break;
                    default:
                        break;
                }
                redirectAttributes.addFlashAttribute("book", bookDto);
                view = new RedirectView("/books/add");
            }
        }

        return view;
    }
}
