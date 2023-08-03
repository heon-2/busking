package org.comfort42.busking.web.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class RegisterReportController {

    record RegisterReportWebRequest(
            String description,
            LocalDateTime createdAt,
            Double lng,
            Double lat,
            Long busNum,
            Long companyId
    ){}

    @PostMapping
    ResponseEntity<?> registerReport(@RequestBody RegisterReportWebRequest req)
    {
        return null;
    }
}
