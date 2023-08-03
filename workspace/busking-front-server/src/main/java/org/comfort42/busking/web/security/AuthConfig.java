package org.comfort42.busking.web.security;

import com.auth0.jwt.JWTVerifier;
import org.comfort42.busking.application.port.inbound.IssueTokenUseCase;
import org.comfort42.busking.application.port.outbound.LoadTokenPort;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.time.Duration;

@Configuration
@EnableWebSecurity
class AuthConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    UserDetailsService userDetailsService(final LoadUserPort loadUserPort) {
        return new CustomUserDetailsService(loadUserPort);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthorizationHeaderFilter authorizationHeaderFilter(final LoadTokenPort loadTokenPort, final JWTVerifier accessTokenVerifier) {
        return new AuthorizationHeaderFilter(loadTokenPort, accessTokenVerifier);
    }

    @Bean
    AuthenticationResultHandler authenticationResultHandler(
            final IssueTokenUseCase issueTokenUseCase,
            final JWTGenerator jwtGenerator,
            @Value("${busking.security.token.access-token-lifetime}") final long accessTokenLifetime,
            @Value("${busking.security.token.refresh-token-lifetime}") final long refreshTokenLifetime) {
        return new AuthenticationResultHandler(
                issueTokenUseCase,
                jwtGenerator,
                Duration.ofSeconds(accessTokenLifetime),
                Duration.ofSeconds(refreshTokenLifetime)
        );
    }

    @Bean
    SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        final AuthenticationResultHandler authenticationResultHandler = context.getBean(AuthenticationResultHandler.class);
        final AuthorizationHeaderFilter authorizationHeaderFilter = context.getBean(AuthorizationHeaderFilter.class);

        return http
                .csrf(csrf -> csrf.disable())
                .addFilterAfter(authorizationHeaderFilter, ExceptionTranslationFilter.class)
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(authenticationResultHandler)
                        .failureHandler(authenticationResultHandler))
                .build();
    }

}
