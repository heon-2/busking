package org.comfort42.busking.application.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {

    @Getter
    private final String id;

    @Getter
    private final String password;

    @Getter
    private final String phone;

    @Getter
    private final String email;

}
