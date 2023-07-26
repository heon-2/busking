package org.comfort42.busking.web.security;

import java.util.Collection;
import java.util.EnumSet;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String password;
    private boolean isAccountLocked = false;
    private boolean isAccountExpired = false;
    private boolean isCredentialsExpired = false;
    private boolean isEnabled = true;

    CustomUserDetails(final User user) {
        id = user.getId();
        password = user.getPassword();
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return EnumSet.noneOf(UserRole.class);
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
