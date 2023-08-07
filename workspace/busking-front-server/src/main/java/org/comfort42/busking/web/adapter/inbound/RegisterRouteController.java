package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.BusCommand;
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

    record RegisterRouteWebRequest(
            List<Long> stations,
            String name
    ){}

    private final RegisterRouteUseCase registerRouteUseCase;

    @PostMapping
    ResponseEntity<?> registerRoute(@RequestBody RegisterRouteWebRequest req, @PathVariable Long companyId){
        try {
            List<Station.StationId> stationIds=new ArrayList<>();
            for(Long id: req.stations()){
                stationIds.add(new Station.StationId(id));
            }
            registerRouteUseCase.registerRoute(new RouteCommand(null,req.name(),stationIds, Company.CompanyId.of(companyId)));
            return ResponseEntity
                    .created(new URI(String.format("/api/companies/%d/routes", companyId)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
