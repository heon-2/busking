package org.comfort42.busking.web;

import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class AuthenticationConfig {

   @Bean
   AuthenticationResultHandler authenticationResultHandler() {
      return new AuthenticationResultHandler();
   }

   @Bean
   UserDetailsService userDetailsService(final LoadUserPort loadUserPort) {
      return new CustomUserDetailsService(loadUserPort);
   }

   @Bean
   PasswordEncoder passwordEncoder() {
      return NoOpPasswordEncoder.getInstance();
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
