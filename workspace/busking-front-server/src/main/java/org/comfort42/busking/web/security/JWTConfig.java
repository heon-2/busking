package org.comfort42.busking.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
class JWTConfig {

    @Bean
    KeyPair rsaKeyPair() throws NoSuchAlgorithmException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("rsa");
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    Algorithm tokenCryptoAlgorithm() throws NoSuchAlgorithmException {
        final KeyPair rsaKeyPair = rsaKeyPair();
        final RSAPublicKey rsaPublicKey = (RSAPublicKey) rsaKeyPair.getPublic();
        final RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();
        return Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
    }

    @Bean
    JWTGenerator jwtGenerator(@Value("${busking.security.token.issuer}") final String tokenIssuer) throws NoSuchAlgorithmException {
        return new JWTGenerator(tokenIssuer, tokenIssuer, tokenCryptoAlgorithm());
    }

    @Bean
    JWTVerifier accessTokenVerifier(@Value("${busking.security.token.issuer}") final String tokenIssuer, final Algorithm tokenCryptoAlgorithm) {
        return JWT.require(tokenCryptoAlgorithm)
                .withIssuer(tokenIssuer)
                .withClaimPresence("iat")
                .withClaimPresence("exp")
                .withClaimPresence("sub")
                .withClaimPresence("tid")
                .withClaimPresence("authorities")
                .build();
    }

    @Bean
    JWTVerifier refreshTokenVerifier(@Value("${busking.security.token.issuer}") final String tokenIssuer, final Algorithm tokenCryptoAlgorithm) {
        return JWT.require(tokenCryptoAlgorithm)
                .withIssuer(tokenIssuer)
                .build();
    }

}
