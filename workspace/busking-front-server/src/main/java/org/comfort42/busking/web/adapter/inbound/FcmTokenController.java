package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.FcmUseCase;
import org.comfort42.busking.application.port.inbound.SaveFcmTokenCommand;
import org.comfort42.busking.application.port.inbound.SendMessageCommand;
import org.comfort42.busking.web.security.TokenAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmUseCase fcmUseCase;
    @PostMapping
    ResponseEntity<?> saveToken(Authentication authentication, @RequestBody SaveFcmTokenCommand saveFcmTokenCommand){
        try {
            if (authentication == null || !(authentication instanceof TokenAuthentication)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
            }else{
                System.out.println(UUID.fromString(authentication.getName()));
                return new ResponseEntity<>(fcmUseCase.saveToken(new SaveFcmTokenCommand(
                        UUID.fromString(authentication.getName()),
                        saveFcmTokenCommand.getToken()
                )),HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/notification")
    ResponseEntity<?> sendMessage(@RequestBody SendMessageCommand sendMessageCommand){
        try {
            return new ResponseEntity<>(fcmUseCase.sendMessage(sendMessageCommand),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
