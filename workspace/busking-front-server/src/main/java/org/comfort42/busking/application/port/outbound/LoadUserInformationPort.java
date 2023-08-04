// TODO(권준일): 네이밍 컨벤션 준수

package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;

public interface LoadUserInformationPort {
    List<User> LoadViewUser();
    User LoadDetailUser();
}
