package org.comfort42.busking.web;

import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    CustomUserDetailsService(final LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                loadUserPort.loadUserById(username)
                        .orElseThrow(() -> new UsernameNotFoundException("")));
    }

}
