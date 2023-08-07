package org.comfort42.busking.web.security;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.IssueTokenUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class AuthenticationResultHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private static final String SUCCESS_RESPONSE_TEMPLATE = "{\"accessToken\": \"%s\",\"refreshToken\":\"%s\"}";
    private final IssueTokenUseCase issueTokenUseCase;
    private final JWTGenerator jwtGenerator;
    private final Duration accessTokenLifetime;
    private final Duration refreshTokenLifetime;

    AuthenticationResultHandler(
            final IssueTokenUseCase issueTokenUseCase,
            final JWTGenerator jwtGenerator,
            final Duration accessTokenLifetime,
            final Duration refreshTokenLifetime) {
        this.issueTokenUseCase = issueTokenUseCase;
        this.jwtGenerator = jwtGenerator;
        this.accessTokenLifetime = accessTokenLifetime;
        this.refreshTokenLifetime = refreshTokenLifetime;
    }

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            final var userDetails = (CustomUserDetails) authentication.getPrincipal();
            final var userId = userDetails.getUserId();
            final var companyId = userDetails.getCompanyId();

            final var token = issueTokenUseCase.issueTokenFor(userId, companyId, accessTokenLifetime, refreshTokenLifetime);
            if (token.isEmpty()) {
                resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return;
            }

            final String accessToken = jwtGenerator.generateAccessToken(token.get(), userDetails);
            final String refreshToken = jwtGenerator.generateRefreshToken(token.get());
            resp.setStatus(HttpStatus.OK.value());
            resp.getWriter().write(String.format(SUCCESS_RESPONSE_TEMPLATE, accessToken, refreshToken));
        }
    }

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest req,
             HttpServletResponse resp,
            final AuthenticationException exception) throws IOException, ServletException {
        // TODO(meo-s): Auto-generated method stub
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
    }

}
