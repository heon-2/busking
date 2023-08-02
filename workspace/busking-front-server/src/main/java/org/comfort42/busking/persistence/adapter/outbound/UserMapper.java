package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.User;

class UserMapper {

    private static UserMapper instance = null;

    static UserMapper getInstance() {
        if (instance != null) {
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
                jpaEntity.getPassword(),
                jpaEntity.getEmail(),
                jpaEntity.getPhoneNumber(),
                Company.CompanyId.of(jpaEntity.getCompanyId()),
                jpaEntity.getRole()
        );
    }

    UserJpaEntity mapToJpaEntity(final User domainEntity) {
        return new UserJpaEntity(
                domainEntity.id().toString(),
                domainEntity.password(),
                domainEntity.email(),
                domainEntity.phone(),
                domainEntity.companyId().value(),
                domainEntity.role(),
                null
        );
    }

}
