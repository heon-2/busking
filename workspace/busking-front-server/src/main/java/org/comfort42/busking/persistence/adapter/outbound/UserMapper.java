package org.comfort42.busking.persistence.adapter;

import org.comfort42.busking.application.domain.model.User;

class UserMapper {
    User mapJpaEntityToDomainEntity(final UserJpaEntity userJpaEntity) {
        return new User(userJpaEntity.getId(), userJpaEntity.getPassword(), null, null);
    }
}
