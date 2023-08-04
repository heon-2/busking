package org.comfort42.busking.application.domain.model;

import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    GUEST("GUEST"),
    EMPLOYEE("EMPLOYEE"),
    COMPANY_ADMIN("COMPANY_ADMIN"),
    DRIVER("DRIVER");

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

        if (COMPANY_ADMIN.value().equals(role)) {
            return COMPANY_ADMIN;
        }else if (DRIVER.value().equals(role)) {
            return DRIVER;
        }else {
            return EMPLOYEE;

        }

        //throw new IllegalArgumentException();
    }

}
