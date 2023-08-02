package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.ViewUserCommand;

import java.util.List;
import java.util.Optional;

public interface LoadViewUserPort {
    Optional<List<User>> LoadViewUser();
    Optional<User> LoadDetailUser();
}
