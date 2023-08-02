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
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

class AuthorizationHeaderFilter extends OncePerRequestFilter {
    private final static String MSG_ILLEGAL_AUTH_TOKEN = "A illegal authorization token was passed.";
    private final static String MSG_AUTH_TOKEN_EXPIRED = "";  // TODO(meo-s): empty exception message
    private final LoadTokenPort loadTokenPort;
    private final JWTVerifier accessTokenVerifier;

    AuthorizationHeaderFilter(final LoadTokenPort loadTokenPort, final JWTVerifier accessTokenVerifier) {
        this.loadTokenPort = loadTokenPort;
        this.accessTokenVerifier = accessTokenVerifier;
    }

    protected void doFilterInternal(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                final String accessToken = authorizationHeader.substring("Bearer ".length()).trim();
                final DecodedJWT decodedJWT = accessTokenVerifier.verify(accessToken);

                final Claim receivedTokenId = decodedJWT.getClaim("tid");
                if (receivedTokenId == null) {
                    throw new BadCredentialsException(MSG_ILLEGAL_AUTH_TOKEN);
                }

                final Instant receivedTokenIssuedAt = decodedJWT.getIssuedAtAsInstant();
                if (receivedTokenIssuedAt == null) {
                    throw new BadCredentialsException(MSG_ILLEGAL_AUTH_TOKEN);
                }

                final Token token = loadTokenPort
                        .loadTokenByUserId(User.UserId.of(decodedJWT.getSubject()))
                        .orElseThrow(() -> new CredentialsExpiredException(MSG_AUTH_TOKEN_EXPIRED));

                if (!token.tokenId().equals(Token.TokenId.of(receivedTokenId.asString()))) {
                    throw new BadCredentialsException(MSG_AUTH_TOKEN_EXPIRED);
                }

//                SecurityContextHolder.getContext().setAuthentication();
                System.out.println("good");
            } catch (final TokenExpiredException e) {
                throw new CredentialsExpiredException(e.toString());
            } catch (final SignatureVerificationException e) {
                throw new BadCredentialsException(e.toString());
            } catch (final JWTDecodeException | IncorrectClaimException | MissingClaimException | AlgorithmMismatchException e) {
                throw new BadCredentialsException(MSG_ILLEGAL_AUTH_TOKEN);
            }
        }

        filterChain.doFilter(req, resp);
    }
}
