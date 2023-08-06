package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/{companyId}/routes")
public class RegisterRouteController {

    record RegisterRouteWebRequest(
            List<Long> stations,
            String name
    ){}

    @PostMapping
    ResponseEntity<?> registerRoute(@RequestBody RegisterRouteWebRequest req, @PathVariable Long companyId){
        try {

            return ResponseEntity
                    .created(new URI(String.format("/api/companies/%d/routes", companyId)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("에러", HttpStatus.BAD_REQUEST);
        }
    }
}
