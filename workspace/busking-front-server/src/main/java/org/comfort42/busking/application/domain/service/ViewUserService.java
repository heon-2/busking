package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.UserViewUseCase;
import org.comfort42.busking.application.port.inbound.ViewUserCommand;

import java.util.List;
import java.util.Optional;

public class ViewUserService implements UserViewUseCase {
    @Override
    public Optional<List<ViewUserCommand>> UserList() {
        return Optional.empty();
    }

    @Override
    public Optional<ViewUserCommand> DetailUser(int userId) {
        return Optional.empty();
    }
}
