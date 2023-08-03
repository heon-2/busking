package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterUserCommand;
import org.comfort42.busking.application.port.inbound.RegisterUserUseCase;
import org.comfort42.busking.application.port.outbound.RegisterUserPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort registerUserPort;

    RegisterUserService(final RegisterUserPort registerUserPort) {
        this.registerUserPort = registerUserPort;
    }

    @Override
    public User registerUser(final RegisterUserCommand cmd) {
        // TODO(meo-s): 입력 검증
        return registerUserPort.regitserUser(cmd);
    }
}
