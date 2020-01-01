package io.tiencong283.blog.validator;

import io.tiencong283.blog.model.UserForm;
import io.tiencong283.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
    private UserService userService;

    @Autowired
    public UserFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        /* basic validations like length and null constrains has been validated */

        // username exists
        if (userService.userExists(userForm.getUsername())) {
            errors.rejectValue("username", "username.duplicate");
        }

        // check complex password
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasLowercase = false;
        for (int i = 0; i < userForm.getPassword().length(); ++i) {
            char c = userForm.getPassword().charAt(i);
            if (Character.isLowerCase(c))
                hasLowercase = true;
            if (Character.isUpperCase(c))
                hasUppercase = true;
            if (Character.isDigit(c))
                hasNumber = true;
            if (hasLowercase && hasUppercase && hasNumber)
                break;
        }
        if (!hasLowercase || !hasUppercase || !hasNumber) { // not satisfy one of them
            errors.rejectValue("password", "password.notComplex");
        }
        // password not match
        if (!userForm.getPassword().equals(userForm.getMatchingPassword())) {
            errors.rejectValue("password", "password.notMatch");
        }
    }
}
