package org.comfort42.busking.application.port.inbound;

public interface FcmUseCase {

    boolean saveToken();
    boolean sendMessage();
}
