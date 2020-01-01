package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.UserForm;
import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.service.UserService;
import io.tiencong283.blog.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class RegistrationController {
    private UserService userService;
    private UserFormValidator userFormValidator;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    public RegistrationController(UserService userService, UserFormValidator userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    /* show the register page */
    @RequestMapping(value = "/register.html", method = {GET, HEAD})
    public String showRegisterPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "registration";
    }

    /* handle register request */
    @RequestMapping(value = "/register.html", method = {POST})
    public String register(@ModelAttribute @Valid UserForm userForm, BindingResult bindingResult) {
        userFormValidator.validate(userForm, bindingResult);    // logic validation
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        this.userService.addUser(WebUser.fromUserForm(userForm));
        return "redirect:/login.html";
    }
}
