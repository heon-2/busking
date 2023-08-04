package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;

public interface ViewUserUseCase {
    // TODO(권준일): 네이밍 컨벤션 준수
    List<ViewUserCommand> UserList();

    User DetailUser(int userId);
}
