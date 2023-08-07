package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/buses/{busNum}/assign")
public class AssignBusRouteController {

    @PostMapping
    ResponseEntity<?> assignBusRoute(@PathVariable Long companyID,@PathVariable Long busNum){
        try{

            return ResponseEntity.ok().body("성공");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
