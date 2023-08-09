package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/companies/{companyId}/buses")
@RequiredArgsConstructor
public class LoadBusController {

    private final LoadBusUseCase loadBusUseCase;
    @GetMapping
    ResponseEntity<?> loadBusList(){
        try{
            return ResponseEntity.ok().body(loadBusUseCase.loadBusList());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{busNum}")
    ResponseEntity<?> loadBusById(@PathVariable Long companyId, @PathVariable Long busNum){
        try{

            return ResponseEntity.ok().body(loadBusUseCase.loadBusById(new BusCommand(companyId,busNum,new ArrayList<>())));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
