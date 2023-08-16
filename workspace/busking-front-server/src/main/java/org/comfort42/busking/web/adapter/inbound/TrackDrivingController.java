package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.LoadRealtimeBusState;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/realtime/driving/track")
@RequiredArgsConstructor
class TrackDrivingController {

    record BusId(long companyId, long no) {
    }

    record TrackDrivingRequestBody(BusId bus) {
    }

    private final ObjectMapper objectMapper;
    private final LoadRealtimeBusState loadRealtimeBusState;

    @PostMapping
    ResponseEntity<?> trackDriving(@RequestBody TrackDrivingRequestBody payload) {
        try {
            if (payload.bus() == null) {
                throw new IllegalArgumentException("bus object is required");
            }

            final var companyId = Company.CompanyId.of(payload.bus().companyId);
            final var obj = objectMapper.createObjectNode();
            obj.put("status", "200 ok");

            if (payload.bus().no() != -1) {
                final var busId = Bus.BusId.of(companyId, payload.bus().no());
                obj.set("data", objectMapper.readValue(loadRealtimeBusState.loadRealtimeBusState(busId), ObjectNode.class));
            } else {
                final var data = obj.putObject("data");
                loadRealtimeBusState
                        .loadAllRealtimeBusState(companyId)
                        .forEach(pair -> {
                            try {
                                data.set(pair.getFirst(), objectMapper.readValue(pair.getSecond(), ObjectNode.class));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(obj);
        } catch (NoSuchElementException e) {
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            final var obj = objectMapper.createObjectNode();
            obj.put("status", "400 bad request");
            obj.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("{\"status\": \"500 internal server error\"}");
        }
    }

}
