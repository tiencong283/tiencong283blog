package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.service.UserService;
import io.tiencong283.blog.validator.UserFormValidator;
import io.tiencong283.blog.view.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class RegistrationController {
    private UserService userService;
    private UserFormValidator userFormValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserFormValidator(UserFormValidator userFormValidator) {
        this.userFormValidator = userFormValidator;
    }

    @RequestMapping(value = "/register.html", method = {GET, HEAD})
    public String showRegisterPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @RequestMapping(value = "/register.html", method = {POST})
    public String register(Model model, @Valid UserForm userForm, BindingResult errors) {
        userFormValidator.validate(userForm, errors);
        if (errors.hasErrors()) {
            model.addAttribute("checking", true);
            return "registration";
        }
        this.userService.addUser(WebUser.fromUserForm(userForm));
        return "redirect:/login.html";
    }
}
