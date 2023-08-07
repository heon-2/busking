package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface ViewUserUseCase {
    // TODO(권준일): 네이밍 컨벤션 준수
    ConcurrentHashMap<String, Object> userList(long companyId,long page);

    ViewUserPayload userDetail(UUID userId);
}

