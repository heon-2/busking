package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.AssignBusToRouteUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/buses/{busNo}/assign")
public class AssignBusRouteController {

    record AssignBusToRouteRequestBody(Long routeId) {
    }

    private final AssignBusToRouteUseCase assignBusRouteUseCase;

    @PostMapping
    ResponseEntity<?> assignBusRoute(
            @PathVariable Long companyId,
            @PathVariable Long busNo,
            @RequestBody AssignBusToRouteRequestBody payload) {

        try {
            final var companyId_ = Company.CompanyId.of(companyId);
            assignBusRouteUseCase.assignBusRoute(Bus.BusId.of(companyId_, busNo), Route.RouteId.of(payload.routeId()));
            return ResponseEntity.ok().body("성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
