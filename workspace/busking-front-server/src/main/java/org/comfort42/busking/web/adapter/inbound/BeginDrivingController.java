package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BeginDrivingController {

    @Value("${busking.gps-mapper.url}")
    String gpsMapperUrl;

    private final LoadRouteUseCase loadRouteUseCase;
    private final LoadBusUseCase loadBusUseCase;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/realtime/driving/begin")
    ResponseEntity<?> beginDriving(@RequestBody ObjectNode map) throws JsonProcessingException {
        try {
            final var busObj = map.get("bus");
            final var routeObj = (ObjectNode) map.get("route");

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("bus", busObj);

            final var companyId = Company.CompanyId.of(routeObj.get("id").asLong());
            final var routeId = Route.RouteId.of(routeObj.get("id").asLong());
            final var route = loadRouteUseCase.loadRouteById(companyId, routeId);

            routeObj.put("geometry", route.getGeometry());
            final var stationsObj = routeObj.putArray("stations");
            for (int i = 0; i < route.getStations().size(); ++i) {
                final var station = route.getStations().get(i);
                final var latlngObj = objectMapper.createObjectNode();
                latlngObj.put("lat", station.lat());
                latlngObj.put("lng", station.lng());
                stationsObj.add(latlngObj);
            }

            requestMap.put("route", routeObj);

            String webclientResponse = WebClient.create()
                    .post()
                    .uri(gpsMapperUrl + "/api/realtime/driving/begin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(requestMap))
                    .retrieve()
                    .onStatus(
                            HttpStatus.INTERNAL_SERVER_ERROR::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new))
                    .onStatus(
                            HttpStatus.BAD_REQUEST::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new))
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(webclientResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
