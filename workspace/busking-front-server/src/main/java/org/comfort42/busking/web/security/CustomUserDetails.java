package org.comfort42.busking.web.security;

import java.util.Collection;
import java.util.EnumSet;

import lombok.Getter;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class CustomUserDetails implements UserDetails {

    @Getter
    private User.UserId userId;

    @Getter
    private String username;

    @Getter
    private String password;

    @Getter
    private String realName;

    @Getter
    private String email;

    @Getter
    private String phoneNumber;

    @Getter
    private Company.CompanyId companyId;

    private UserRole userRole;

    private boolean isAccountLocked = false;

    private boolean isAccountExpired = false;

    private boolean isCredentialsExpired = false;

    private boolean isEnabled = true;

    CustomUserDetails(
            final User.UserId userId,
            final String username,
            final String password,
            final String realName,
            final String email,
            final String phoneNumber,
            final Company.CompanyId companyId,
            final UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyId = companyId;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return EnumSet.of(userRole);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
