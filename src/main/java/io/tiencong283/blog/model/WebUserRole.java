package io.tiencong283.blog.model;

import lombok.Getter;

@Getter
public enum WebUserRole {
    /*
        ROLE ADMIN can manage posts, users
        ROLE USER can only post comments
     */
    USER_ROLE("USER"),
    ADMIN_ROLE("ADMIN");

    private String roleName;

    WebUserRole(String roleName) {
        this.roleName = roleName;
    }
}
