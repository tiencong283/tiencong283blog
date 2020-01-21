package io.tiencong283.blog.validator;

import io.tiencong283.blog.model.UserForm;
import io.tiencong283.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserFormValidator implements Validator {
    // user policy
    private static int usernameMaxLen = 15;
    private static Pattern usernamePattern = Pattern.compile("\\w+");
    // password policy
    private static int passowrdMin = 10;
    private static Pattern passwordPattern = Pattern.compile("\\S+");
    // email policy
    private static Pattern emailPattern = Pattern.compile("(\\w+)(\\.\\w+)*@(\\w+)(\\.\\w+)+");
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
        /* basic validations like empty and null constrains has been validated */

        // username check
        String username = userForm.getUsername();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (!errors.hasFieldErrors("username")) {    // pass empty check
            if (username.length() > usernameMaxLen) {
                errors.rejectValue("username", "username.size.max");
            }
            if (!usernamePattern.matcher(username).matches()) {
                errors.rejectValue("username", "username.pattern");
            }
            if (!errors.hasFieldErrors("username") && userService.userExists(username)) {    // pass size and pattern check
                errors.rejectValue("username", "username.duplicate");
            }
        }

        // password check
        String password = userForm.getPassword();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (!errors.hasFieldErrors("password")) {    // pass emtpy check
            if (password.length() < passowrdMin) {
                errors.rejectValue("password", "password.size.min");
            }
            if (!passwordPattern.matcher(password).matches()) {
                errors.rejectValue("password", "password.pattern");
            }
            if (!errors.hasFieldErrors("password")) {    // pass size and pattern check
                // check complex password
                boolean hasUppercase = false;
                boolean hasNumber = false;
                boolean hasLowercase = false;
                for (int i = 0; i < password.length(); ++i) {
                    char c = password.charAt(i);
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
            }
        }

        // password not match
        String matchingPassword = userForm.getMatchingPassword();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matchingPassword", "empty");
        if (!errors.hasFieldErrors("password") && !password.equals(matchingPassword)) {
            errors.rejectValue("matchingPassword", "matchingPassword.notMatch");
        }

        // email check
        String email = userForm.getEmail();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty");
        if (!errors.hasFieldErrors("email") && !emailPattern.matcher(email).matches()) {
            errors.rejectValue("email", "email.pattern");
        }
    }
}
