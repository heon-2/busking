package org.comfort42.busking.persistence.adapter.outbound;

import java.util.Optional;

import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
class LoadUserPersistenceAdapter implements LoadUserPort {

    @PersistenceContext
    private EntityManager em;

    private final UserMapper userMapper = new UserMapper();

    @Override
    public Optional<User> loadUserById(final String userId) {
        try {
            final UserJpaEntity userJpaEntity = em
                    .createQuery("SELECT u FROM UserJpaEntity u WHERE u.id=:userId", UserJpaEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.of(userMapper.mapJpaEntityToDomainModel(userJpaEntity));
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }

}
