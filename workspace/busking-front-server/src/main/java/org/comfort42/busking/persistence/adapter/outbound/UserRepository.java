package org.comfort42.busking.persistence.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity,Long> {
}
