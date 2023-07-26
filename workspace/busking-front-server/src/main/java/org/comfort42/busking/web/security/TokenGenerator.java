package org.comfort42.busking.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

class TokenGenerator {

    record GenerationOptions(
            Algorithm tokenCryptoAlgorithm,
            String tokenIssuer,
            String tokenAudience,
            long accessTokenLifetime,
            long refreshTokenLifetime) {
    }

    @Getter
    @Setter
    private GenerationOptions defaultTokenGenerationOptions = null;

    TokenGenerator(final GenerationOptions defaultTokenGenerationOptions) {
        this.defaultTokenGenerationOptions = defaultTokenGenerationOptions;
    }

    Optional<Token> generate(
            final UserDetails userDetails,
            final Algorithm tokenCryptoAlgorithm,
            final String tokenIssuer,
            final String tokenAudience,
            final long accessTokenLifetime,
            final long refreshTokenLifetime) {

        final UUID tokenId = UUID.randomUUID();
        final Instant now = Instant.now();
        final String authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(";"));

        final String accessToken = JWT
                .create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(accessTokenLifetime))
                .withIssuer(tokenIssuer)
                .withAudience(tokenAudience)
                .withClaim("tid", tokenId.toString())
                .withClaim("authorities", authorities)
                .sign(tokenCryptoAlgorithm);

        final String refreshToken = JWT
                .create()
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(refreshTokenLifetime))
                .withIssuer(tokenIssuer)
                .withAudience(tokenId.toString())
                .sign(tokenCryptoAlgorithm);

        return Optional.of(new Token(tokenId, now, accessToken, refreshToken));
    }

    Optional<Token> generate(final UserDetails userDetails, final String tokenAudience, final GenerationOptions tokenGenerationOptions) {
        return generate(userDetails,
                tokenGenerationOptions.tokenCryptoAlgorithm,
                tokenGenerationOptions.tokenIssuer,
                tokenAudience,
                tokenGenerationOptions.accessTokenLifetime,
                tokenGenerationOptions.refreshTokenLifetime);
    }

    Optional<Token> generate(final UserDetails userDetails, final GenerationOptions tokenGenerationOptions) {
        return generate(userDetails,
                tokenGenerationOptions.tokenCryptoAlgorithm,
                tokenGenerationOptions.tokenIssuer,
                tokenGenerationOptions.tokenAudience,
                tokenGenerationOptions.accessTokenLifetime,
                tokenGenerationOptions.refreshTokenLifetime);
    }

    Optional<Token> generate(final UserDetails userDetails) {
        return defaultTokenGenerationOptions != null
                ? generate(userDetails, defaultTokenGenerationOptions)
                : Optional.empty();
    }

}
