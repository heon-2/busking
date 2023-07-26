package org.comfort42.busking.web.security;

import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.web.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    protected CustomUserDetailsService(final LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                loadUserPort.loadUserById(username)
                        .orElseThrow(() -> new UsernameNotFoundException("")));  // TODO: 예외 메시지 작성하기
    }

}
