package org.comfort42.busking.web.security;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;

import java.util.EnumSet;

public class UserMapper {

    private static UserMapper instance = null;

    static UserMapper getInstacne() {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    static void destroyInstance() {
        instance = null;
    }

    private UserMapper() {
    }

    User mapUserDetailsToDomainModel(final CustomUserDetails userDetails) {
        // TODO(meo-s): UserDetails에 저장된 authorities에서 EnumSet 만들어서 전달하기
        return new User(
                User.UserId.of(userDetails.getUsername()),
                userDetails.getPassword(),
                null,
                null,
                null,
                UserRole.of(userDetails.getAuthorities().stream().findFirst().get().toString())
        );
    }

    CustomUserDetails mapDomainModelToUserDetails(final User domainModel) {
        return new CustomUserDetails(domainModel.id().toString(), domainModel.password());
    }

}
