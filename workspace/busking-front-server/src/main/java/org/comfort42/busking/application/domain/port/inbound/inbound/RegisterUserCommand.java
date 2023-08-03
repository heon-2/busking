package org.comfort42.busking.application.domain.port.inbound.inbound;

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
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userPhoneNumber;
    private Company.CompanyId userCompany;
    private UserRole userRole;
}
