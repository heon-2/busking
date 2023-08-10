package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.FcmToken;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.outbound.SaveFcmTokenPort;
import org.comfort42.busking.common.PersistenceAdapter;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@PersistenceAdapter
public class SaveFcmTokenPersistenceAdapter implements SaveFcmTokenPort {
    private final SaveFcmTokenJpaRepository saveFcmTokenJpaRepository;
    @Override
    public void update(UUID userId, String token) {
        saveFcmTokenJpaRepository.updateFcmToken(token,userId);
    }

    @Override
    public void save(UUID userId, String token) {
        saveFcmTokenJpaRepository.saveFcmToken(userId,token);
    }

    @Override
    public Optional<FcmTokenJpaEntity> findByUserId(UUID userId) {

        return saveFcmTokenJpaRepository.findByUserId(userId);
    }

    @Override
    public String findUserIdInUserTable(String userId) {
        Optional<FcmTokenJpaEntity> fcm = saveFcmTokenJpaRepository.findUserIdInUserTable(userId);
        if(fcm.isPresent()){
            return fcm.get().getToken();
        }else{
            return null;
        }
    }
}
