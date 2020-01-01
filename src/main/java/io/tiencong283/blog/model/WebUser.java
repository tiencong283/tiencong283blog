package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
public class WebUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private WebUserRole role = WebUserRole.USER_ROLE;

    @Transient
    private User user;  // core user information implementation of UserDetails

    public WebUser() {
    }

    public WebUser(String username, String password, String email) {
        this(username, password, email, WebUserRole.USER_ROLE);
    }

    public WebUser(String username, String password, String email, WebUserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createUser();
    }

    public void createUser() {
        this.user = new User(username, password, getAuthorities());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public WebUserRole getRole() {
        return this.role;
    }

    /*
        All AuthenticationProviders included with the security architecture use SimpleGrantedAuthority to populate
        the Authentication object.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(getRole().getRoleName()));
    }

    /* all of the related-metadata methods below return false */
    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public static WebUser fromUserForm(UserForm userForm){
        return new WebUser(userForm.getUsername(), userForm.getPassword(), userForm.getEmail());
    }
}
