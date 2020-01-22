package io.tiencong283.blog.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "{empty}")
    private String username;

    @NotEmpty(message = "{empty}")
    private String password;

    @NotEmpty(message = "{empty}")
    private String matchingPassword;

    @NotEmpty(message = "{empty}")
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
        this.email = StringUtils.trimWhitespace(email);
    }
}

