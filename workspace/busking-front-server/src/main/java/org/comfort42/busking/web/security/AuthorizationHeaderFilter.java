package org.comfort42.busking.web.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.UserRole;
import org.comfort42.busking.application.port.outbound.LoadTokenPort;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        final String BEARER_TOKEN_PREFIX = "Bearer ";

        final String authorizationHeader = req.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return null;
        }

        return authorizationHeader.substring(BEARER_TOKEN_PREFIX.length());
    }

    private Pair<Token, DecodedJWT> verifyToken(final String accessToken) {
        try {
            final var decodedJWT = accessTokenVerifier.verify(accessToken);

            final var tokenId = decodedJWT.getClaim("tid");
            if (decodedJWT.getSubject() == null || tokenId.isMissing()) {
                throw new BadCredentialsException("");  // TODO(meo-s): empty exception message
            }

            final var tokenSubject = Token.TokenSubject.of(decodedJWT.getSubject());

            final var token = loadTokenPort
                    .loadToken(tokenSubject)
                    .orElseThrow(() -> new BadCredentialsException(""));  // TODO(meo-s): empty exception message

            if (!token.tokenId().equals(Token.TokenId.of(tokenId.asString()))) {
                throw new BadCredentialsException("");  // TODO(meo-s): empty exception message
            }

            return Pair.of(token, decodedJWT);
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
            final var tokenInfo = verifyToken(bearerToken);
            final var tokenMetadata = tokenInfo.getFirst();
            final var tokenPayload = tokenInfo.getSecond();

            final var authorities = tokenPayload.getClaim("authorities").isNull()
                    ? tokenPayload.getClaim("authorities").asString() : "GUEST";

            final var webAuthenticationDetails = new WebAuthenticationDetails(req.getRemoteAddr(), tokenMetadata.tokenId().toString());
            final var authentication = new TokenAuthentication(tokenMetadata, UserRole.of(authorities), webAuthenticationDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // TODO(meo-s): 예외 전환
        }

        filterChain.doFilter(req, resp);
    }

}