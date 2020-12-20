package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.LibrarySettingDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.EntityAlreadyExistsException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NonUpdatableFieldException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.ValueNotCompatibleWithTypeException;
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

        model.addAttribute(ViewConstants.FRAGMENT, "library-setting/list");
        return view;
    }

    @PostMapping("/edit")
    public RedirectView addSetting(@ModelAttribute @Valid LibrarySettingDto settingDto, BindingResult result,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
        } else {
            try {
                librarySettingService.save(settingDto);
                redirectAttributes.addFlashAttribute("success", true);
            } catch (NonUpdatableFieldException e) {
                switch (e.getName()) {
                    case "name":
                        ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                                "Ayar adını değiştiremezsin.");
                        break;
                    case "type":
                        ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                                "Ayar değer tipini değiştiremezsin.");
                        break;
                    default:
                        break;
                }
            } catch (ValueNotCompatibleWithTypeException e) {
                ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                        "Ayar tipi ve değeri uyuşmuyor.");
            } catch (EntityAlreadyExistsException e) {
                ModelAttributeHelper.addFlashAttrHasErrorIntegrityErrorAndErrorMessage(redirectAttributes,
                        settingDto.getName() + " ismiyle zaten bir ayar bulunuyor.");
            }
        }

        return new RedirectView("/library-settings");
    }

}
