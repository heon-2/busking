package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.User;

class UserMapper {
    User mapJpaEntityToDomainEntity(final UserJpaEntity userJpaEntity) {
        // TODO(meo-s): phone/email 속성 추가하기
        return new User(userJpaEntity.getId(), userJpaEntity.getPassword(), null, null);
    }
}
