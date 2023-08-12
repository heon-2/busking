package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/companies/{companyId}/buses")
@RequiredArgsConstructor
public class LoadBusController {

    private final LoadBusUseCase loadBusUseCase;
    @GetMapping
    ResponseEntity<?> loadAllBuses(@PathVariable long companyId){
        try{
            final var buses = loadBusUseCase.loadAllBuses(Company.CompanyId.of(companyId));
            return ResponseEntity
                    .ok()
                    .body(buses);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{busNo}")
    ResponseEntity<?> loadBusById(@PathVariable Long companyId, @PathVariable Long busNo){
        try{
            final var companyId_ = Company.CompanyId.of(companyId);
            final var busId = Bus.BusId.of(companyId_, busNo);
            return ResponseEntity.ok().body(loadBusUseCase.loadBusById(busId));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
