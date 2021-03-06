package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.service.UserService;
import io.tiencong283.blog.validator.UserFormValidator;
import io.tiencong283.blog.view.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
public class AuthController {
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

    @RequestMapping(value = "/login.html", method = {GET, HEAD})
    public String showLoginPage(Principal principal){
        if (principal != null){ // authenticated
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = "/register.html", method = {GET, HEAD})
    public String showRegisterPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping("/register.html")
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
