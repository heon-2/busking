package org.comfort42.busking.application.domain.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.FcmToken;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.FcmUseCase;
import org.comfort42.busking.application.port.inbound.SaveFcmTokenCommand;
import org.comfort42.busking.application.port.inbound.SendMessageCommand;
import org.comfort42.busking.application.port.outbound.SaveFcmTokenPort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.FcmTokenJpaEntity;


import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@UseCase
@Transactional
public class FcmService implements FcmUseCase {

    private final SaveFcmTokenPort saveFcmTokenPort;
    @Override
    public boolean saveToken(SaveFcmTokenCommand saveFcmTokenCommand) {
        //해당 유저의 아이디를 찾기
        Optional<FcmTokenJpaEntity> fcmToken = saveFcmTokenPort.findByUserId(saveFcmTokenCommand.getUserId());
            //토큰 테이블에 있는지 확인
            if(fcmToken.isPresent()){
                //있을 때 토큰 저장
                saveFcmTokenPort.update(saveFcmTokenCommand.getUserId(),
                                        saveFcmTokenCommand.getToken());
                return true;
            }else{
                //없을 때 토큰 저장
                saveFcmTokenPort.save(saveFcmTokenCommand.getUserId(),
                                      saveFcmTokenCommand.getToken());
                return true;
            }

    }

    @Override
    public boolean sendMessage(SendMessageCommand sendMessageCommand) throws FirebaseMessagingException {
        //해당 아이디를 가진 멤버의 id를 찾고
        String registrationToken = saveFcmTokenPort.findUserIdInUserTable(sendMessageCommand.getUserId());
        if(registrationToken!=null){
            //찾은 id로 send
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setBody("화상채팅 요청이 들어왔습니다.")
                            .setTitle("알림")
                            .setImage("null")
                            .build())
                    .setToken(registrationToken)
                    .build();
            FirebaseMessaging.getInstance().send(message);
            return true;
        }else{
            return false;
        }
    }
}
