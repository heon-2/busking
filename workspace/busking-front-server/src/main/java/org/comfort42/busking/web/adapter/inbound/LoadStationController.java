package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.LoadStationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies/{companyId}/stations")
@RequiredArgsConstructor
public class LoadStationController {

    private final LoadStationUseCase loadStationUseCase;

    @GetMapping
    ResponseEntity<?> loadStationList(@PathVariable Long companyId){
        try{
            return ResponseEntity.ok().body(loadStationUseCase.loadStationList(Company.CompanyId.of(companyId)));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
