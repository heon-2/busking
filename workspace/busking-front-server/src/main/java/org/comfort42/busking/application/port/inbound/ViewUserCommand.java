package org.comfort42.busking.application.port.inbound;

public record ViewUserCommand(String username, String email, String phoneNumber, String realName, String role) {

    public ViewUserCommand(
            String username,
            String email,
            String phoneNumber,
            String realName,
            String role) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.realName = realName;
        this.role = role;
    }

}
