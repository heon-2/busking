package org.comfort42.busking.persistence.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaveFcmTokenJpaRepository extends JpaRepository<FcmTokenJpaEntity,Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into fcm_token (user_id, token) values (?1, ?2)", nativeQuery = true)
    void saveFcmToken(UUID userId, String token);

    @Transactional
    @Modifying
    @Query(value = "update fcm_token set token = ?1 where user_id = ?2", nativeQuery = true)
    void updateFcmToken(String token, UUID userId);


    Optional<FcmTokenJpaEntity> findByUserId(UUID userId);

    @Query(value = " SELECT * FROM busking.fcm_token where user_id = (select id from busking.user where username = :userId);", nativeQuery = true)
    Optional<FcmTokenJpaEntity> findUserIdInUserTable( @Param(value = "userId") String userId);



}
