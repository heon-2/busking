package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AdjustGpsController {
    @Value("${busking.gps-mapper.url}")
    String gpsMapperUrl;

    private final ObjectMapper objectMapper;

    @PutMapping("/api/realtime/driving/drive")
    ResponseEntity<?> adjustGps(@RequestBody ObjectNode map) throws JsonProcessingException {
        final var busObj=map.get("bus");
        final var gpsObj=map.get("gps");

        Map<String,Object> requestMap = new HashMap<>();

        requestMap.put("bus",busObj);
        requestMap.put("gps",gpsObj);

        try{
            String webclientResponse= WebClient.create()
                    .put()
                    .uri(gpsMapperUrl+"/api/realtime/driving/drive")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(map))
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
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }



    }
}
