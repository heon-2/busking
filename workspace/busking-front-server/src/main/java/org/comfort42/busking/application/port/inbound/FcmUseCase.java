package org.comfort42.busking.application.port.inbound;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.UUID;

public interface FcmUseCase {

    boolean saveToken(SaveFcmTokenCommand saveFcmTokenCommand);
    boolean sendMessage(SendMessageCommand sendMessageCommand) throws FirebaseMessagingException;
}
