package org.comfort42.busking.application.port.inbound;

public record ViewUserPayload(String username, String email, String phoneNumber, String realName,String companyId, String role) {

    public ViewUserPayload(
            String username,
            String email,
            String phoneNumber,
            String realName,
            String companyId,
            String role) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.realName = realName;
        this.companyId = companyId;
        this.role = role;
    }

}
