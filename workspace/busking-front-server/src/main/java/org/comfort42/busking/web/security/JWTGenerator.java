package org.comfort42.busking.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.comfort42.busking.application.domain.model.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

class JWTGenerator {

    private final Algorithm tokenCryptoAlgorithm;
    private final String tokenIssuer;
    private final String tokenAudience;

    JWTGenerator(final String tokenIssuer, final String tokenAudience, final Algorithm tokenCryptoAlgorithm) {
        this.tokenCryptoAlgorithm = tokenCryptoAlgorithm;
        this.tokenIssuer = tokenIssuer;
        this.tokenAudience = tokenAudience;
    }

    String generateAccessToken(final Token token, final UserDetails userDetails) {
        final String authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(";"));

        return JWT
                .create()
                .withIssuedAt(token.issuedAt())
                .withNotBefore(token.issuedAt())
                .withExpiresAt(token.accessExpiresAt())
                .withIssuer(tokenIssuer)
                .withSubject(userDetails.getUsername())
                .withAudience(tokenAudience)
                .withClaim("tid", token.tokenId().toString())
                .withClaim("authorities", authorities)
                .sign(tokenCryptoAlgorithm);
    }

    String generateRefreshToken(final Token token) {
        return JWT
                .create()
                .withIssuedAt(token.issuedAt())
                .withNotBefore(token.issuedAt())
                .withExpiresAt(token.refreshExpiresAt())
                .withIssuer(tokenIssuer)
                .withAudience(token.tokenId().toString())
                .sign(tokenCryptoAlgorithm);
    }

}
