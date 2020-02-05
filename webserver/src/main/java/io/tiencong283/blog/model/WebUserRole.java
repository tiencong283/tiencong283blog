package io.tiencong283.blog.model;

import lombok.Getter;

@Getter
public enum WebUserRole {
    /*
        ROLE ADMIN can manage posts, users
        ROLE USER can only post comments
     */
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String roleName;

    WebUserRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
