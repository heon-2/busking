package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.FcmToken;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenMapper {
    final static UserMapper userMapper = UserMapper.getInstance();

//    public FcmTokenJpaEntity mapToJpaEntity(FcmToken fcmToken){
//        return new FcmTokenJpaEntity(fcmToken.getId(),
//                fcmToken.getUserId(),
//                fcmToken.getToken());
//    }
}
