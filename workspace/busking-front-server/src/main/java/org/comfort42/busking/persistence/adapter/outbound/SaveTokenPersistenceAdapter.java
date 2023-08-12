package org.comfort42.busking.persistence.adapter.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.port.outbound.SaveTokenPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
class SaveTokenPersistenceAdapter implements SaveTokenPort {
    private final ObjectMapper objectMapper;

    private final ValueOperations<String, String> redisValueOps;

    SaveTokenPersistenceAdapter(
            final ObjectMapper objectMapper,
            @Qualifier("buskingLoginRedisTemplate") final RedisTemplate<String, String> buskingLoginRedisTemplate) {
        this.objectMapper = objectMapper;
        this.redisValueOps = buskingLoginRedisTemplate.opsForValue();
    }

    @Override
    public void save(final Token token) {
        final TokenRedisEntity redisEntity = new TokenRedisEntity(
                token.tokenId().toString(),
                token.subject().toString(),
                token.issuedAt(),
                token.accessExpiresAt(),
                token.refreshExpiresAt()
        );

        try {
            final String tokenJson = objectMapper.writeValueAsString(redisEntity);
            redisValueOps.set(redisEntity.getSubject(), tokenJson);
        } catch (final JsonProcessingException e) {
            // TODO(meo-s): empty catch body
        }
    }

}
