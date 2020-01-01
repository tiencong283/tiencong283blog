package io.tiencong283.blog.model;

import lombok.Getter;

@Getter
public enum WebUserRole {
    USER_ROLE("USER"),
    ADMIN_ROLE("ADMIN");

    private String roleName;

    WebUserRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
