package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterReportUseCase;
import org.comfort42.busking.application.port.inbound.ReportCommand;
import org.comfort42.busking.web.security.TokenAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class RegisterReportController {

    record RegisterReportWebRequest(
            String description,
            LocalDateTime createdAt,
            Double lng,
            Double lat,
            Long busNum,
            Long companyId
    ) {
    }

    private final RegisterReportUseCase reportUseCase;

    @PostMapping
    ResponseEntity<?> registerReport(
            @RequestBody final RegisterReportWebRequest req,
            final Authentication authentication) {
        if (authentication == null || !(authentication instanceof TokenAuthentication)) {
            // 사용자가 로그인을 하지 않으면 null이다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
        }
        try {
            final var userId = User.UserId.of(authentication.getName());
            reportUseCase.registerReport(
                    new ReportCommand(
                            null,
                            userId,
                            req.description(),
                            LocalDateTime.now(),
                            req.lng(),
                            req.lat(),
                            req.busNum(),
                            req.companyId(),
                            null
                    ));
            return ResponseEntity.created(new URI(new String("/api/reports"))).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }

    }
}
