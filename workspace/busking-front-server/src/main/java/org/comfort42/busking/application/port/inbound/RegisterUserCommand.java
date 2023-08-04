package org.comfort42.busking.application.port.inbound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phoneNumber;
    private Company.CompanyId company;
    private UserRole role;
}
