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

            final var latlng = objectMapper.createObjectNode();
            latlng.put("lat", loc.latitude());
            latlng.put("lng", loc.longitude());

            final var obj = objectMapper.createObjectNode();
            obj.put("status", "200 ok");
            obj.set("data", latlng);

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
