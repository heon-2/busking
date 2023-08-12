package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.RegisterBusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/companies/{companyId}/buses/register")
@RequiredArgsConstructor
public class RegisterBusController {

    record RegisterBusRequestBody(Long busNum) {
    }

    private final RegisterBusUseCase registerBusUseCase;

    @PostMapping
    ResponseEntity<?> registerBus(@RequestBody RegisterBusRequestBody req,
                                  @PathVariable Long companyId) {
        try {
            registerBusUseCase.registerBus(Bus.BusId.of(Company.CompanyId.of(companyId), req.busNum()));
            return ResponseEntity
                    .created(new URI(String.format("/api/companies/%d/buses/%d", companyId, req.busNum())))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

}
