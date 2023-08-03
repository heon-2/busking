package org.comfort42.busking.application.domain.port.inbound.outbound;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterUserCommand;

public interface RegisterUserPort {
    User regitserUser(RegisterUserCommand cmd);
}
