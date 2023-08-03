package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.User;

public interface RegisterUserUseCase {
    User registerUser(RegisterUserCommand cmd);
}
