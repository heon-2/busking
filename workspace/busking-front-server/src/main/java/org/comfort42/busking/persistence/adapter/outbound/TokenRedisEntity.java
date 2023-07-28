package org.comfort42.busking.persistence.adapter.outbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class TokenRedisEntity {
    private String tid;
    private String subject;
    private Instant issuedAt;
    private Instant accessExpiresAt;
    private Instant refreshExpiresAt;
}
