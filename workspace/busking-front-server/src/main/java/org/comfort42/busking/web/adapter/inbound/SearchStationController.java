package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.LoadStationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/companies/{companyId}/stations")
@RequiredArgsConstructor
class SearchStationController {

    private final ObjectMapper objectMapper;
    private final LoadStationUseCase loadStationUseCase;

    @GetMapping("/search")
    ResponseEntity<?> searchStationByName(@RequestParam(value = "stationName", required = true) final String stationName) {
        if (stationName.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"400 bad request\", \"message\":\"stationName is required\"}");
        }

        try {
            final var station = loadStationUseCase.loadStationByName(stationName);
            final var respObj = objectMapper.createObjectNode();
            respObj.put("status", "200 ok");
            respObj.putPOJO("data", station);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(respObj);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"404 not found\"}");
        }
    }

}
