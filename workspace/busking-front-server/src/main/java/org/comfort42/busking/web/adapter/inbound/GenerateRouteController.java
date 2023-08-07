package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
class GenerateRouteController {

    record GenerateRouteWebRequestPayload(List<List<Float>> hints) {
    }

    private final ObjectMapper objectMapper;

    GenerateRouteController(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    String buildOsrmApiRequestUrl(final String osrmApiServerUrl, final List<List<Float>> hints) {
        final var requestUrl = new StringBuilder();
        requestUrl.append(osrmApiServerUrl);
        requestUrl.append("/route/v1/driving/");

        for (final List<Float> hint : hints) {
            requestUrl.append(hint.get(1));
            requestUrl.append(',');
            requestUrl.append(hint.get(0));
            requestUrl.append(';');
        }

        requestUrl.deleteCharAt(requestUrl.length() - 1);
        requestUrl.append("?overview=full&steps=false&alternatives=false");

        return requestUrl.toString();
    }

    ResponseEntity<?> convertOsrmRouteResponse(final String osrmRouteResp) throws JsonProcessingException {
        final var tree = objectMapper.readValue(osrmRouteResp, ObjectNode.class);
        if (!tree.get("code").asText().equals("Ok")) {
            return ResponseEntity
                    .internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"FAIL\"}");
        }

        final var route = tree.get("routes").get(0);

        final var routeObj = objectMapper.createObjectNode();
        routeObj.put("distance", route.get("distance"));
        routeObj.put("geometry", route.get("geometry"));

        final var resp = objectMapper.createObjectNode();
        resp.put("status", "OK");
        resp.put("route", routeObj);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp);
    }

    @PostMapping("/generate")
    ResponseEntity<?> generateRoute(
            @Value("${busking.osrm.url}") final String osrmApiServerUrl,
            @RequestBody final GenerateRouteWebRequestPayload payload) {

        if (payload.hints.size() < 2) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"status\": \"fail\", \"msg\": \"경로 생성을 위해선 최소 두 개의 경유지가 설정되어야 합니다.\"");
        }

        final String osrmResp = WebClient.create()
                .get()
                .uri(buildOsrmApiRequestUrl(osrmApiServerUrl, payload.hints))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return convertOsrmRouteResponse(osrmResp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }
    }

}
