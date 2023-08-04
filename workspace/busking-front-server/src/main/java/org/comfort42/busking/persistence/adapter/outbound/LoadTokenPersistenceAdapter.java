package org.comfort42.busking.persistence.adapter.outbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadTokenPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class LoadTokenPersistenceAdapter implements LoadTokenPort {
    private static final TokenMapper tokenMapper  = TokenMapper.getInstance();
    private final ObjectMapper objectMapper;
    private final ValueOperations<String, String> redisValueOps;

    LoadTokenPersistenceAdapter(final ObjectMapper objectMapper, final RedisTemplate<String, String> redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisValueOps = redisTemplate.opsForValue();
    }

    @Override
    public Optional<Token> loadToken(final Token.TokenSubject subject) {
        final String tokenRedisEntityJson = redisValueOps.get(subject.toString());
        if (tokenRedisEntityJson == null) {
            return Optional.empty();
        }

        try {
            final TokenRedisEntity tokenRedisEntity = objectMapper.readValue(tokenRedisEntityJson, TokenRedisEntity.class);
            final Token token = tokenMapper.mapRedisEntityToDomainModel(tokenRedisEntity);
            return Optional.of(token);
        } catch (final JsonProcessingException e) {
            // TODO(meo-s): empty catch block body
            return Optional.empty();
        }
    }
}
