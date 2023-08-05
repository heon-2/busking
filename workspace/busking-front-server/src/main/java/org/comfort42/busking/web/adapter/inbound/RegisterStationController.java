package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.RegisterStaionUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class RegisterStationController {

    record RegisterStationWebRequest(String name,
                                     Double lng,
                                     Double lat){}

    private final RegisterStaionUseCase registerStaionUseCase;

    @PostMapping
    ResponseEntity<?> registerStation(@RequestBody RegisterStationWebRequest req){
        try{
            registerStaionUseCase.registerStation(new StationCommand(null,req.name(),req.lng(),req.lat()));
            return ResponseEntity.created(new URI("/api/stations")).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }

    }
}
