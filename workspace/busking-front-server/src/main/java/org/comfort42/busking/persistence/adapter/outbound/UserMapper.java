package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.User;

class UserMapper {

    private static UserMapper instance = null;

    static UserMapper getInstance() {
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

    User mapToDomainEntity(final UserJpaEntity jpaEntity) {
        return new User(
                User.UserId.of(jpaEntity.getId()),
                jpaEntity.getUsername(),
                jpaEntity.getPassword(),
                jpaEntity.getRealName(),
                jpaEntity.getEmail(),
                jpaEntity.getPhoneNumber(),
                Company.CompanyId.of(jpaEntity.getCompanyId()),
                jpaEntity.getRole());
    }

    UserJpaEntity mapToJpaEntity(final User domainEntity) {
        return new UserJpaEntity(
                domainEntity.id().value(),
                domainEntity.username(),
                domainEntity.password(),
                domainEntity.realName(),
                domainEntity.email(),
                domainEntity.phoneNumber(),
                domainEntity.companyId().value(),
                domainEntity.role(),
                null);
    }

}
