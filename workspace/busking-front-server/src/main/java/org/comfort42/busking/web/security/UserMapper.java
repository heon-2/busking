package org.comfort42.busking.web.security;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.UserRole;

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

    User mapToDomainModel(final CustomUserDetails userDetails) {
        // TODO(meo-s): UserDetails에 저장된 authorities에서 EnumSet 만들어서 전달하기
        return new User(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getRealName(),
                userDetails.getEmail(),
                userDetails.getPhoneNumber(),
                userDetails.getCompanyId(),
                UserRole.of(userDetails.getAuthorities().stream().findFirst().get().toString())
        );
    }

    CustomUserDetails mapToUserDetails(final User domainModel) {
        return new CustomUserDetails(
                domainModel.id(),
                domainModel.username(),
                domainModel.password(),
                domainModel.realName(),
                domainModel.email(),
                domainModel.phoneNumber(),
                domainModel.companyId(),
                domainModel.role()
        );
    }

}
