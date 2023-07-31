package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;

import java.util.EnumSet;

class UserMapper {
    User mapJpaEntityToDomainModel(final UserJpaEntity userJpaEntity) {
        // TODO(meo-s): phone/email, userRoles 속성 추가하기
        return new User(
                User.UserId.of(userJpaEntity.getId()),
                userJpaEntity.getPassword(),
                null,
                null,
                EnumSet.of(UserRole.NORMAL)
        );
    }
}
