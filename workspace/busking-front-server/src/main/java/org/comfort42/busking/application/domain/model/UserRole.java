package org.comfort42.busking.application.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    NORMAL("NORMAL");

    private String role;

    private UserRole(final String role) {
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

}
