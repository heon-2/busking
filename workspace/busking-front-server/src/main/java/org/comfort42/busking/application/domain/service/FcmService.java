package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.port.inbound.FcmUseCase;

public class FcmService implements FcmUseCase {
    @Override
    public boolean saveToken() {
        //해당 유저의 아이디를 찾고
        //토큰 저장

        return false;
    }

    @Override
    public boolean sendMessage() {

        //해당 아이디를 가진 멤버의 id를 찾고
        //찾은 id로 send
        return false;
    }
}
