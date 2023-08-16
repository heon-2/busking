package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.web.adapter.inbound.schema.BusIdObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;

@RestController
@RequestMapping("/api/realtime/driving")
@RequiredArgsConstructor
class JoinDrivingController {

    record JoinDrivingRequestBody(BusIdObject bus, long destination) {
    }

    private final ObjectMapper objectMapper;

    @PostMapping("/join")
    ResponseEntity<?> joinDriving(
            @Value("${busking.gps-mapper.url}") final String gpsMapperUrl,
            @RequestBody final JoinDrivingRequestBody payload) throws JsonProcessingException {

        if (payload.bus() == null) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"400 bad request\", \"message\": \"$.busId is required\"}");
        }

        if (payload.destination() < 0) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"400 bad request\", \"message\": \"$.destination must be positive\"");
        }

        try {
            return WebClient.create()
                    .post()
                    .uri(gpsMapperUrl + "/api/realtime/driving/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(payload))
                    .retrieve()
                    .toEntity(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            final var statusCode = e.getStatusCode();
            return ResponseEntity
                    .status(statusCode)
                    .contentType(Objects.requireNonNull(e.getHeaders().getContentType()))
                    .body(e.getResponseBodyAsString());
        }

    }

}
