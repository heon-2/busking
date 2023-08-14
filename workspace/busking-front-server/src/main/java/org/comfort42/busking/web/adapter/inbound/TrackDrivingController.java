package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.LoadLocationEstimationUseCase;
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
    private final LoadLocationEstimationUseCase loadLocationEstimationUseCase;

    @PostMapping
    ResponseEntity trackDriving(@RequestBody TrackDrivingRequestBody payload) {
        try {
            // loc is abbreviation for location
            final var loc = loadLocationEstimationUseCase.loadLocationEstimation(
                    Company.CompanyId.of(payload.bus().companyId()), payload.bus().no());

            final var locJson = objectMapper.createObjectNode();

            final var rawLatLng = objectMapper.createObjectNode();
            rawLatLng.put("lat", loc.rawLatitude());
            rawLatLng.put("lng", loc.rawLongitude());

            final var raw = objectMapper.createObjectNode();
            raw.put("timestamp", loc.at());
            raw.set("latlng", rawLatLng);

            locJson.set("raw", raw);

            if (loc.adjustedAt() != -1) {
                final var adjLatLng = objectMapper.createObjectNode();
                adjLatLng.put("lat", loc.rawLatitude());
                adjLatLng.put("lng", loc.rawLongitude());

                final var adj = objectMapper.createObjectNode();
                adj.put("timestamp", loc.adjustedAt());
                adj.set("latlng", adjLatLng);

                locJson.set("adj", adj);
            } else {
                locJson.set("adj", null);
            }

            final var obj = objectMapper.createObjectNode();
            obj.put("status", "200 ok");
            obj.set("data", locJson);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(obj);
        } catch (NoSuchElementException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("{\"status\": \"500 internal server error\"}");
        }
    }

}
