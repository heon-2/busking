package org.comfort42.busking.application.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class FcmToken {

    private FcmTokenId id;

    private final User userId;

    private final String token;



    @Value
    public static class FcmTokenId{
        private final Long value;
    }

    public static FcmToken withId(
            FcmTokenId fcmTokenId,
            User user,
            String token
    ){
        return new FcmToken(fcmTokenId,user,token);
    }
}
