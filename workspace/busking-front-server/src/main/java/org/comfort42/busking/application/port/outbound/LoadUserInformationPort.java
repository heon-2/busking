package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;

public interface LoadUserInformationPort {
    List<User> LoadViewUser();
    User LoadDetailUser();
}
