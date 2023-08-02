package org.comfort42.busking.application.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    EMPLOYEE("EMPLOYEE"),
    COMPANY_ADMIN("COMPANY_ADMIN");

    private String role;

    private UserRole(final String role) {
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

    public String value() {
        return role;
    }

    public static UserRole of(final String role) {
        if (EMPLOYEE.value().equals(role)) {
            return EMPLOYEE;
        }
        if (COMPANY_ADMIN.value().equals(role)) {
            return COMPANY_ADMIN;
        }

        throw new IllegalArgumentException();
    }

}
