package org.comfort42.busking.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;
import org.comfort42.busking.application.port.inbound.ViewUserCommand;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;
import org.comfort42.busking.application.port.outbound.LoadUserInformationPort;

import org.comfort42.busking.common.UseCase;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@UseCase
public class ViewUserService implements ViewUserUseCase {

    private final LoadUserInformationPort loadViewUserPort;

    @Override
    public List<ViewUserCommand> UserList() {


        List<ViewUserCommand> list = new ArrayList<>();
        for(User user : loadViewUserPort.LoadViewUser()){
            list.add(new ViewUserCommand(
                    user.id().toString(),
                    user.email(),
                    user.phone(),
                    user.name(),
                    user.role().value()));
        }
        return list;

    }

    @Override
    public User DetailUser(int userId) {
        loadViewUserPort.LoadDetailUser();
        return null;
    }
}
