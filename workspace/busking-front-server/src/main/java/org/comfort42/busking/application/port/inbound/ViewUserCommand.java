package org.comfort42.busking.application.port.inbound;

import jakarta.validation.constraints.NotNull;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.User.UserId;
import org.comfort42.busking.application.domain.model.UserRole;

import javax.management.relation.Role;

public record ViewUserCommand(
        @NotNull UserId id,
        @NotNull String email,
        @NotNull String phone,

        @NotNull Company.CompanyId companyId,

        @NotNull UserRole role

        ) {

    public ViewUserCommand(
            UserId id,
            String email,
            String phone,
            Company.CompanyId companyId,
            UserRole role) {
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.companyId = companyId;
            this.role = role;
    }
}
