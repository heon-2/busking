package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterUserCommand;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.application.port.outbound.RegisterUserPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserRepository implements RegisterUserPort, LoadUserPort {

    private final UserMapper userMapper = UserMapper.getInstance();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User regitserUser(final RegisterUserCommand cmd) {
        final UserJpaEntity userJpaEntity = new UserJpaEntity(cmd.getUserId(), cmd.getUserPassword(), cmd.getUserEmail(), cmd.getUserPhoneNumber(), cmd.getUserCompany().value(), cmd.getUserRole(), null);
        em.persist(userJpaEntity);
        return userMapper.mapToDomainEntity(userJpaEntity);
    }

    @Override
    public Optional<User> loadUserById(final String userId) {
        try {
            final UserJpaEntity jpaEntity = em
                    .createQuery("SELECT u FROM UserJpaEntity  u WHERE u.id=:userId", UserJpaEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(userMapper.mapToDomainEntity(jpaEntity));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
