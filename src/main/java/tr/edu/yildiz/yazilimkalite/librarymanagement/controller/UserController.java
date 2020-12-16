package tr.edu.yildiz.yazilimkalite.librarymanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.UserRegistrationDto;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.EntityAlreadyExistsException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.NotExistingEntityException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.exception.UserPasswordEmptyException;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.User;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserRole;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.UserRoleService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.service.UserService;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ModelAttributeHelper;
import tr.edu.yildiz.yazilimkalite.librarymanagement.util.ViewConstants;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/add")
    public ModelAndView showUserAddForm(Model model) {
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        if (model.getAttribute("user") == null) {
            model.addAttribute("user", new UserRegistrationDto());
        }
        
        model.addAttribute("roles", userRoleService.getAllUserRoles());
        model.addAttribute(ViewConstants.FRAGMENT, "user/useradd :: user-add-form");

        return view;
    }

    @PostMapping("/add")
    public RedirectView addUser(@ModelAttribute @Valid UserRegistrationDto user, BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        RedirectView view;
        if (bindingResult.hasErrors()) {
            logger.error("/users/add -- Binding Error: {}", bindingResult);
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            view = new RedirectView("add");
        } else {
            try {
                redirectAttributes.addFlashAttribute("user", user);
                redirectAttributes.addFlashAttribute(ViewConstants.SUCCESS, true);
                userService.saveUser(user);
                view = new RedirectView("/users/edit/" + user.getEmail());
            } catch (EntityAlreadyExistsException e) {
                logger.info("Exception at /users/add: \n{}", e.getLocalizedMessage());
                redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
                redirectAttributes.addFlashAttribute(ViewConstants.Error.INTEGRITY_ERROR, true);
                redirectAttributes.addFlashAttribute("emailExists", true);
                view = new RedirectView("add");
            } catch (UserPasswordEmptyException e) {
                logger.info("Exception at /users/add: \n{}", e.getLocalizedMessage());
                redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
                redirectAttributes.addFlashAttribute(ViewConstants.Error.INTEGRITY_ERROR, true);
                redirectAttributes.addFlashAttribute("passwordEmpty", true);
                redirectAttributes.addFlashAttribute("user", user);
                view = new RedirectView("add");
            } catch (NotExistingEntityException e) {
                redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
                view = new RedirectView("/users/add");
            }
        }

        return view;
    }

    @GetMapping({ "/", "" })
    public String showUserIndex(Model model) {
        model.addAttribute("users", userService.getPaginatedUsers(PageRequest.of(0, 10)));
        model.addAttribute(ViewConstants.FRAGMENT, "user/userindex :: usertable");
        return ViewConstants.BOILERPLATE;
    }

    @GetMapping("/edit/{email}")
    public ModelAndView showUserEditForm(@PathVariable String email, Model model) {
        User user = userService.getUserByEmail(email);
        ModelAndView view = new ModelAndView(ViewConstants.BOILERPLATE);

        model.addAttribute("email", email);
        model.addAttribute(ViewConstants.UPDATE_FORM, true);

        if (user == null) {
            ModelAttributeHelper.addNotFoundFragmentAndErrorMessage(model, email + " e-posta adresli üye bulunamadı.");
            view.setStatus(HttpStatus.NOT_FOUND);
        } else {
            List<UserRole> roles = userRoleService.getAllUserRoles();
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            model.addAttribute(ViewConstants.FRAGMENT, "user/useradd :: user-add-form");
        }
        return view;
    }

    @PostMapping("/edit/{email}")
    public RedirectView editUser(@PathVariable String email, @ModelAttribute @Valid UserRegistrationDto editedUser,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        RedirectView view = null;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            return new RedirectView(email);
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
            redirectAttributes.addFlashAttribute("notFound", true);
            view = new RedirectView(email);
        } else {
            try {
                userService.editUser(user, editedUser);
                redirectAttributes.addFlashAttribute(ViewConstants.SUCCESS, true);
                view = new RedirectView(email);
            } catch (EntityAlreadyExistsException e) {
                redirectAttributes.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
                redirectAttributes.addFlashAttribute(ViewConstants.Error.INTEGRITY_ERROR, true);
                redirectAttributes.addFlashAttribute("emailExists", true);
                view = new RedirectView(email);
            } catch (Exception e) {
                logger.error("POST: /users/edit/{} \n{}", email, e);
                view = new RedirectView("/500");
                view.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        return view;
    }

}
