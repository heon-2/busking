package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.FcmToken;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.SaveFcmTokenCommand;
import org.comfort42.busking.persistence.adapter.outbound.FcmTokenJpaEntity;


import java.util.Optional;
import java.util.UUID;

public interface SaveFcmTokenPort {
    void update(UUID userId, String token);
    void save(UUID userId, String token);

    Optional<FcmTokenJpaEntity> findByUserId(UUID userId);

    String findUserIdInUserTable(String userId);


}
