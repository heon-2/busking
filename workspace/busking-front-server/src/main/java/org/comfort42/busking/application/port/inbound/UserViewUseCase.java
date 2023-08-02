package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserViewUseCase {
    Optional<List<ViewUserCommand>> UserList();
    Optional<ViewUserCommand> DetailUser(int userId);
}
