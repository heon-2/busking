

package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface LoadUserInformationPort {
    List<User> loadViewUser(long companyId, long page);

    Long totalPage(long companyId);
    User loadUserDetail(UUID userId);
}
