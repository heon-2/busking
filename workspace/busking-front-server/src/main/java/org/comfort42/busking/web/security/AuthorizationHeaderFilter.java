package org.comfort42.busking.web.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadTokenPort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

class AuthorizationHeaderFilter extends OncePerRequestFilter {

    private final LoadTokenPort loadTokenPort;
    private final JWTVerifier accessTokenVerifier;

    AuthorizationHeaderFilter(final LoadTokenPort loadTokenPort, final JWTVerifier accessTokenVerifier) {
        this.loadTokenPort = loadTokenPort;
        this.accessTokenVerifier = accessTokenVerifier;
    }

    private String extractBearerToken(final HttpServletRequest req) {
        final String BEARER_TOKEN_PREFIX = "Bearer";

        final String authorizationHeader = req.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return null;
        }

        return authorizationHeader.substring(BEARER_TOKEN_PREFIX.length());
    }

    private Token verifyToken(final String accessToken) {
        try {
            final DecodedJWT decodedJWT = accessTokenVerifier.verify(accessToken);

            final Claim tokenId = decodedJWT.getClaim("tid");
            if (decodedJWT.getSubject() == null || tokenId.isMissing()) {
                throw new BadCredentialsException("");  // TODO(meo-s): empty exception message
            }

            final User.UserId userId = User.UserId.of(decodedJWT.getSubject());

            final Token token = loadTokenPort
                    .loadTokenByUserId(userId)
                    .orElseThrow(() -> new BadCredentialsException(""));  // TODO(meo-s): empty exception message

            if (!token.tokenId().equals(Token.TokenId.of(tokenId.asString()))) {
                throw new BadCredentialsException("");  // TODO(meo-s): empty exception message
            }

            return token;
        } catch (TokenExpiredException | SignatureVerificationException e) {
            throw e;
        } catch (JWTDecodeException e) {
            throw e;
        }
    }

    protected void doFilterInternal(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final FilterChain filterChain) throws ServletException, IOException {

        final String bearerToken = extractBearerToken(req);
        if (bearerToken == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        try {
            final Token token = verifyToken(bearerToken);

            new WebAuthenticationDetails(req.getRemoteAddr(), token.tokenId().toString());
        }
        catch (Exception e) {
            // TODO(meo-s): 예외 전환
        }

        filterChain.doFilter(req, resp);
    }

}