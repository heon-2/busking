package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterCompanyUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RegisterUserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RegisterCompanyUseCase registerCompanyUseCase;

    @Test
    @Transactional
    void 정상적인_데이터가_주어지면_회원가입을_할_수_있다() throws JsonProcessingException {
        Assertions.assertDoesNotThrow(() -> {
            final Company company = registerCompanyUseCase.registerCompany("busking");
            final var payload = new RegisterUserController.RegisterUserWebRequest(
                    "busking",
                    "busking",
                    "busking@busking.org",
                    "010-1111-1111",
                    0L
            );

            final var req = MockMvcRequestBuilders
                    .post("/api/users")
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload));

            mockMvc
                    .perform(req)
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.header().exists("Location"));
        });

    }

}
