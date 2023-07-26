package org.comfort42.busking.web.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class AuthenticationResultHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final AuthenticationException exception) throws IOException, ServletException {
        // TODO(meo-s): Auto-generated method stub
    }

}
