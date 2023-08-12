package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.RouteDirection;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.RegisterRouteCommand;
import org.comfort42.busking.application.port.inbound.RegisterRouteUseCase;
import org.comfort42.busking.application.port.inbound.RouteCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/routes")
public class RegisterRouteController {

    record RegisterRouteRequestBody(
            List<Long> stations,
            String name,
            String geometry,
            String direction) {
    }

    private final RegisterRouteUseCase registerRouteUseCase;

    @PostMapping
    ResponseEntity<?> registerRoute(@PathVariable Long companyId, @RequestBody RegisterRouteRequestBody payload) {
        final var stations = payload.stations()
                .stream()
                .map(Station.StationId::of)
                .toList();

        try {
            final var cmd = new RegisterRouteCommand(
                    Company.CompanyId.of(companyId),
                    payload.name(),
                    payload.geometry(),
                    RouteDirection.of(payload.direction()),
                    stations
            );
            registerRouteUseCase.registerRoute(cmd);

            return ResponseEntity
                    .created(new URI(String.format("/api/companies/%d/routes", companyId)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
