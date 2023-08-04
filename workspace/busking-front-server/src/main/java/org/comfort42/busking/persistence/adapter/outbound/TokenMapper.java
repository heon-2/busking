package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;

class TokenMapper {
    private static TokenMapper instance;

    static TokenMapper getInstance() {
        if (instance == null) {
            instance = new TokenMapper();
        }
        return instance;
    }

    static void destroyInstance() {
        instance = null;
    }

    TokenRedisEntity mapDomainModelToRedisEntity(final Token domainModel) {
        return new TokenRedisEntity(
                domainModel.tokenId().toString(),
                domainModel.subject().toString(),
                domainModel.issuedAt(),
                domainModel.accessExpiresAt(),
                domainModel.refreshExpiresAt()
        );
    }

    Token mapRedisEntityToDomainModel(final TokenRedisEntity redisEntity) {
        return new Token(
                Token.TokenId.of(redisEntity.getTid()),
                Token.TokenSubject.of(redisEntity.getSubject()),
                redisEntity.getIssuedAt(),
                redisEntity.getAccessExpiresAt(),
                redisEntity.getRefreshExpiresAt()
        );
    }
}
