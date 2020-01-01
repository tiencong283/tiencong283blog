package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserForm {
    // username policy
    @NotEmpty   // it doesnt do any stripping whitespace
    @Size(max = 15, message = "{username.size.max}")
    @Pattern(regexp = "\\w+", message = "{username.pattern}")
    private String username;

    // password policy
    @NotEmpty
    @Size(min = 10, message = "{password.size.min}")
    @Pattern(regexp = "\\S+", message = "{password.pattern}")
    private String password;
    @NotEmpty
    private String matchingPassword;

    // email
    @NotEmpty
    private String email;

    public UserForm() {
    }

    public void setUsername(String username) {
        this.username = StringUtils.trimWhitespace(username);
    }

    public void setPassword(String password) {
        this.password = StringUtils.trimWhitespace(password);
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = StringUtils.trimWhitespace(matchingPassword);
    }

    public void setEmail(String email) {
        this.email = StringUtils.trimWhitespace(email);;
    }
}

