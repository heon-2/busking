package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.RegisterBusCommand;
import org.comfort42.busking.application.port.inbound.RegisterBusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/companies/{companyId}/buses/register")
@RequiredArgsConstructor
public class RegisterBusController {

    record RegisterBusWebRequest(Long busNum) {
    }

    private final RegisterBusUseCase registerBusUseCase;

    @PostMapping
    ResponseEntity<?> registerBus(@RequestBody RegisterBusWebRequest req,
                                  @PathVariable Long companyId,
                                  final Authentication authentication) {
        try {
            registerBusUseCase.registerBus(new RegisterBusCommand(companyId, req.busNum()));
            return ResponseEntity
                    .created(new URI(String.format("/api/companies/%d/buses/register", companyId)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

}
