package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
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
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class EndDrivingController {
    @Value("${busking.gps-mapper.url}")
    String gpsMapperUrl;

    private final ObjectMapper objectMapper;


    @PostMapping("/api/realtime/driving/end")
    ResponseEntity<?> endDriving(@RequestBody ObjectNode map){
        try{
            final var busObj = map.get("bus");

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("bus",busObj);

            String webClientResponse = WebClient.create()
                    .post()
                    .uri(gpsMapperUrl+"/api/realtime/driving/end")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(requestMap))
                    .retrieve()
                    .onStatus(
                            HttpStatus.INTERNAL_SERVER_ERROR::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new))
                    .onStatus( HttpStatus.BAD_REQUEST::equals,
                            response -> response.bodyToMono(String.class).map(Exception::new))
                    .bodyToMono(String.class)
                    .block();



            return new ResponseEntity<String>(webClientResponse,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
