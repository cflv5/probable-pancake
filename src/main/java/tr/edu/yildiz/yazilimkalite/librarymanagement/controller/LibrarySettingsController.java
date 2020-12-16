package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.LibrarySettingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NonUpdatableFieldException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySetting;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.LibrarySettingService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ModelAttributeHelper;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/library-settings")
public class LibrarySettingsController {
    @Autowired
    private LibrarySettingService librarySettingService;

    @GetMapping({ "", "/" })
    public ModelAndView showAllSettings(Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        List<LibrarySetting> settings = librarySettingService.getAll();
        model.addAttribute("settings", settings);

        model.addAttribute(ViewConstants.FRAGMENT, "library-settings/list");
        return view;
    }

    @GetMapping("/add")
    public ModelAndView getAddForm(Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);
        model.addAttribute(ViewConstants.FRAGMENT, "settings/add-form");

        model.addAttribute("setting", new LibrarySettingDto());

        return view;
    }

    @PostMapping("/add")
    public RedirectView addSetting(@ModelAttribute @Valid LibrarySettingDto settingDto, BindingResult result,
            RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            try {
                librarySettingService.save(settingDto);
                redirectAttributes.addFlashAttribute("success", true);
            } catch (NonUpdatableFieldException e) {
                ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                        "Ayar adını değiştiremezsin.");
            }
            view = new RedirectView("/library-settings");
        }

        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@PathVariable Long id, Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);
        LibrarySetting setting = librarySettingService.getById(id);

        if (setting == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model,
                    id + " numaralı kütüphane ayarı bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            model.addAttribute(ViewConstants.FRAGMENT, "settings/add-form");
            model.addAttribute("setting", setting);
        }
        return view;
    }

}
