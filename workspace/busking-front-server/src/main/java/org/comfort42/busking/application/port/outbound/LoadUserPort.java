package org.comfort42.busking.application.port.outbound;

import java.util.Optional;

import org.comfort42.busking.application.domain.model.User;

public interface LoadUserPort {
    Optional<User> loadUserById(String userId);
}
