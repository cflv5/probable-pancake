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

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.WriterDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.WriterService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ModelAttributeHelper;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    private WriterService writerService;

    @GetMapping({ "/", "" })
    public ModelAndView getWriterDashBoard(Model model) {
        Page<Writer> writers = writerService.getPaginated(PageRequest.of(0, 10));

        model.addAttribute("writers", writers);

        model.addAttribute(ViewConstants.FRAGMENT, "");

        return new ModelAndView(ViewConstants.BOILERPLATE);
    }

    @GetMapping("/add")
    public String getAddWriterForm(Model model) {
        if (model.getAttribute(ViewConstants.WRITER) == null) {
            model.addAttribute(ViewConstants.WRITER, new WriterDto());
        }

        model.addAttribute(ViewConstants.FRAGMENT, "writer/writeradd :: writer-add-form");

        return ViewConstants.BOILERPLATE;
    }

    @PostMapping("/add")
    public RedirectView addWriter(@ModelAttribute @Valid WriterDto writerDto, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            Writer writer = writerService.save(writerDto);

            redirectAttributes.addFlashAttribute("success", redirectAttributes);
            view = new RedirectView("/writers/" + writer.getId());
        }
        return view;
    }

    @GetMapping("/edit/{writerId}")
    public ModelAndView getWriterEditForm(@PathVariable Long writerId, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);
        Writer writer = writerService.getWriterById(writerId);

        model.addAttribute(ViewConstants.UPDATE_FORM, true);
        if (writer == null) {
            model.addAttribute(ViewConstants.FRAGMENT, "error/notfound :: callout");
            model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, "Yazar bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "writer/writeradd :: writer-add-form");
            model.addAttribute(ViewConstants.WRITER, writer);
        }

        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView getWriterProfile(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        Writer writer = writerService.getWriterById(id);

        if (writer == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model, "Yazar bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "writer/writer-profile :: profile");
            model.addAttribute(ViewConstants.WRITER, writer);

        }
        return view;
    }

}
