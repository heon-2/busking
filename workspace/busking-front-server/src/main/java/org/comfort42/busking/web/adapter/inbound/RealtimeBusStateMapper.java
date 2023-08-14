package org.comfort42.busking.web.adapter.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.springframework.data.util.Pair;

class RealtimeBusStateMapper {

    private  static RealtimeBusStateMapper instance = null;

    static RealtimeBusStateMapper getInstance() {
        if (instance == null) {
            instance = new RealtimeBusStateMapper();
        }
        return instance;
    }

    ObjectNode mapToJsonObject(final ObjectMapper objectMapper, final RealtimeBusState domainEntity) {
        final var jsonObj = objectMapper.createObjectNode();

        final var rawLatLng = objectMapper.createObjectNode();
        rawLatLng.put("lat", domainEntity.rawLatitude());
        rawLatLng.put("lng", domainEntity.rawLongitude());

        final var raw = objectMapper.createObjectNode();
        raw.put("timestamp", domainEntity.at());
        raw.set("latlng", rawLatLng);

        jsonObj.set("raw", raw);

        if (domainEntity.adjustedAt() != -1) {
            final var adjLatLng = objectMapper.createObjectNode();
            adjLatLng.put("lat", domainEntity.adjustedLatitude());
            adjLatLng.put("lng", domainEntity.adjustedLongitude());

            final var adj = objectMapper.createObjectNode();
            adj.put("timestamp", domainEntity.adjustedAt());
            adj.set("latlng", adjLatLng);

            jsonObj.set("adj", adj);
        } else {
            jsonObj.set("adj", null);
        }

        return jsonObj;
    }

    ObjectNode mapToJsonObject(final ObjectMapper objectMapper, final Pair<String, RealtimeBusState> pair) {
        final var obj = objectMapper.createObjectNode();
        obj.set(pair.getFirst(), mapToJsonObject(objectMapper, pair.getSecond()));
        return obj;
    }

}
