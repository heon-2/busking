package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{company-id}/routes")
public class LoadRouteController {

    public record RoutePayload(Route.RouteId routeId,
                        String name,
                        Company.CompanyId companyId,
                        List<StationCommand> stations){}

    private final LoadRouteUseCase loadRouteUseCase;

    @GetMapping
    ResponseEntity<?> loadRouteList(){
        try{
            return ResponseEntity.ok().body(loadRouteUseCase.loadRouteList());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{route-id}")
    ResponseEntity<?> loadRouteById(Route.RouteId routeId){
        try{
            return ResponseEntity.ok().body(loadRouteUseCase.loadRouteById());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
