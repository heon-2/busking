package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/routes")
public class LoadRouteController {

    public record RoutePayload(Route.RouteId routeId,
                               String name,
                               Company.CompanyId companyId,
                               List<StationCommand> stations,
                               String geometry) {
    }

    private final LoadRouteUseCase loadRouteUseCase;

    @GetMapping
    ResponseEntity<?> loadRouteList(@PathVariable Long companyId) {
        try {
            return ResponseEntity.ok().body(loadRouteUseCase.loadRouteList(Company.CompanyId.of(companyId)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{routeId}")
    ResponseEntity<?> loadRouteById(@PathVariable Long companyId, @PathVariable Long routeId) {
        try {
            return ResponseEntity.ok().body(loadRouteUseCase.loadRouteById(Company.CompanyId.of(companyId), new Route.RouteId(routeId)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
