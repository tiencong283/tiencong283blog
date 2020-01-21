package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class UserForm {
    private String username;
    private String password;
    private String matchingPassword;
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

