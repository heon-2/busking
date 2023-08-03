package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterUserCommand;

public interface RegisterUserPort {
    User regitserUser(RegisterUserCommand cmd);
}
