// TODO(권준일): 네이밍 컨벤션 준수, 페이지네이션으로 구현

package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.ViewUserCommand;
import org.comfort42.busking.application.port.inbound.ViewUserUseCase;

import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.common.UseCase;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@UseCase
public class ViewUserService implements ViewUserUseCase {

    private final LoadUserPort loadUserPort;

    @Override
    public List<ViewUserCommand> UserList() {

        List<ViewUserCommand> list = new ArrayList<>();
//        for (final User user : loadUserPort.LoadViewUser()) {
//            list.add(new ViewUserCommand(
//                    user.id().toString(),
//                    user.email(),
//                    user.phoneNumber(),
//                    user.realName(),
//                    user.role().value()));
//        }

        return list;
    }

    @Override
    public User DetailUser(int userId) {
//        loadViewUserPort.LoadDetailUser();
        return null;
    }

}
