package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.AssignBusRouteUseCase;
import org.comfort42.busking.persistence.adapter.outbound.BusPK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/buses/{busNum}/assign")
public class AssignBusRouteController {

    record AssignBusRouteWebRequest(Long routeId){}

    private final AssignBusRouteUseCase assignBusRouteUseCase;

    @PostMapping
    ResponseEntity<?> assignBusRoute(@PathVariable Long companyId, @PathVariable Long busNum, @RequestBody AssignBusRouteWebRequest req){
        try{
            assignBusRouteUseCase.assignBusRoute(new BusPK(companyId,busNum),new Route.RouteId(req.routeId()));
            return ResponseEntity.ok().body("성공");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
