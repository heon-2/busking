package org.comfort42.busking.application.port.inbound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.User;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class SaveFcmTokenCommand {
    private final UUID userId;
    private final String token;
}
