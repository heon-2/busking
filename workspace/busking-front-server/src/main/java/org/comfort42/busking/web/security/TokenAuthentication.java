package org.comfort42.busking.web.security;

import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

public class TokenAuthentication implements Authentication {

    private final Token token;
    private final UserDetails userDetails;
    private final WebAuthenticationDetails webAuthenticationDetails;
    private boolean isAuthenticated = true;

    TokenAuthentication(final Token token, final UserRole role, final WebAuthenticationDetails webAuthenticationDetails) {
        this.token = token;
        this.webAuthenticationDetails = webAuthenticationDetails;
        this.userDetails = new CustomUserDetails(
                token.subject().userId(),
                null,
                null,
                null,
                null,
                null,
                token.subject().companyId(),
                role
        );
    }

    @Override
    public String getName() {
        return token.subject().userId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return webAuthenticationDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    public String getCompanyId() {
        return token.subject().companyId().toString();
    }

}
