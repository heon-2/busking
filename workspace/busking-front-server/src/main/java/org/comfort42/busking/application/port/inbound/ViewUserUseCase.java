package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;

public interface ViewUserUseCase {
    List<ViewUserCommand> UserList();
    User DetailUser(int userId);
}
