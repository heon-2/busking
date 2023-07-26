package org.comfort42.busking.web.auth;

import com.auth0.jwt.algorithms.Algorithm;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.web.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
class AuthConfig {

    @Bean
    UserDetailsService userDetailsService(final LoadUserPort loadUserPort) {
        return new CustomUserDetailsService(loadUserPort);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

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
    TokenGenerator

    @Bean
    AuthenticationResultHandler authenticationResultHandler() {
        return new AuthenticationResultHandler();
    }

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/v1/auth/signin")
                        .successHandler(authenticationResultHandler())
                        .failureHandler(authenticationResultHandler()))
                .build();
    }

}
