package org.comfort42.busking.application.domain.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.UserRole;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterCompanyUseCase;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterUserCommand;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterUserUseCase;
import org.comfort42.busking.application.domain.port.inbound.outbound.RegisterCompanyPort;
import org.comfort42.busking.application.domain.port.inbound.outbound.RegisterUserPort;
import org.comfort42.busking.application.domain.service.RegisterCompanyService;
import org.comfort42.busking.application.domain.service.RegisterUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RegisterCompanyService.class, RegisterCompanyPort.class, RegisterUserService.class, RegisterUserPort.class})
public class RegisterUserUseCaseTests {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;
    @Autowired
    private RegisterCompanyUseCase registerCompanyUseCase;

    @Test
    void 정상적인_데이터가_주어지면_회원가입을_할_수_있다() {
        Assertions.assertDoesNotThrow(() -> {
            final Company company = registerCompanyUseCase.registerCompany("busking");

            final RegisterUserCommand cmd = new RegisterUserCommand(
                    "busking",
                    "busking",
                    "busking@busking.org",
                    "010-1111-2222",
                    company.id(),
                    UserRole.EMPLOYEE
            );

            registerUserUseCase.registerUser(cmd);
        });
    }

}
