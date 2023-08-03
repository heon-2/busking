package org.comfort42.busking.persistence.adapter.outbound;


import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.UserRole;
import org.comfort42.busking.application.domain.service.RegisterCompanyService;
import org.comfort42.busking.application.domain.service.RegisterUserService;
import org.comfort42.busking.application.port.inbound.RegisterCompanyUseCase;
import org.comfort42.busking.application.port.inbound.RegisterUserCommand;
import org.comfort42.busking.application.port.inbound.RegisterUserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RegisterCompanyService.class, CompanyRepository.class, RegisterUserService.class, UserRepository.class})
public class UserRepositoryTests {

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
