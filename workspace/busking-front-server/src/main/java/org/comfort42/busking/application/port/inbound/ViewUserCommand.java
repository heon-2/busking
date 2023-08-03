package org.comfort42.busking.application.port.inbound;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.domain.model.User.UserId;
import org.comfort42.busking.application.domain.model.UserRole;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;


public record ViewUserCommand(
        String id,
        String email,
        String phone,

        String name,

        String role

        ) {

    public ViewUserCommand(
            String id,
            String email,
            String phone,
            String name,
            String role) {
            this.id = id;
            this.email = email;
            this.phone = phone;
            this.name = name;
            this.role = role;
    }


}
