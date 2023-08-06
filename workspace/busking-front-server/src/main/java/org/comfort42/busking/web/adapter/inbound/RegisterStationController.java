package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.RegisterStaionUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/companies/{companyId}/stations")
@RequiredArgsConstructor
public class RegisterStationController {

    record RegisterStationWebRequest(String name,
                                     Double lng,
                                     Double lat){}

    private final RegisterStaionUseCase registerStaionUseCase;

    @PostMapping
    ResponseEntity<?> registerStation(@RequestBody RegisterStationWebRequest req, @PathVariable Long companyId){
        try{
            registerStaionUseCase.registerStation(new StationCommand(null,req.name(),req.lng(),req.lat(), Company.CompanyId.of(companyId)));
            return ResponseEntity.created(new URI("/api/stations")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }

    }
}
