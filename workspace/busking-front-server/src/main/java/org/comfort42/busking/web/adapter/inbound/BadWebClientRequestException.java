package org.comfort42.busking.web.adapter.inbound;

import lombok.Getter;

@Getter
public class BadWebClientRequestException extends RuntimeException {

    private final int statusCode;

    public BadWebClientRequestException(int statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }

}