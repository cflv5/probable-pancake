package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

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

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.PublisherDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Publisher;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.PublisherService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @GetMapping({ "/", "" })
    public ModelAndView getPublisherDashBoard(Model model) {
        Page<Publisher> publishersPage = publisherService.getPaginated(PageRequest.of(0, 10));

        model.addAttribute(ViewConstants.FRAGMENT, "");

        model.addAttribute("publishers", publishersPage);

        return new ModelAndView(ViewConstants.BOILERPLATE);
    }

    @GetMapping("/{id}")
    public ModelAndView getPublisherProfile(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Publisher publisher = publisherService.getPublisherById(id);

        if (publisher == null) {
            model.addAttribute(ViewConstants.FRAGMENT, "error/notfound :: callout");
            model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, "Yayımcı firma bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "publisher/publisher-profile");
            model.addAttribute(ViewConstants.PUBLISHER, publisher);

        }
        return view;
    }

    @GetMapping("/add")
    public String getAddPublisherForm(Model model) {
        if(model.getAttribute(ViewConstants.PUBLISHER) == null) {
            model.addAttribute(ViewConstants.PUBLISHER, new PublisherDto());
        }

        model.addAttribute(ViewConstants.FRAGMENT, "publisher/publisheradd :: add-form");

        return ViewConstants.BOILERPLATE;
    }

    @PostMapping("/add")
    public RedirectView addPublisher(@ModelAttribute @Valid PublisherDto publisher, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            publisherService.savePublisher(publisher);
            redirectAttributes.addFlashAttribute("success", true);
            view = new RedirectView("/publishers");
        }
        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getPublisherEditForm(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Publisher publisher = publisherService.getPublisherById(id);

        model.addAttribute(ViewConstants.UPDATE_FORM, true);
        if (publisher == null) {
            model.addAttribute(ViewConstants.FRAGMENT, "error/notfound :: callout");
            model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, "Yayımcı firma bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "publisher/publisheradd :: add-form");
            model.addAttribute(ViewConstants.PUBLISHER, publisher);
        }

        return view;
    }
}
