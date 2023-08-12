package org.comfort42.busking.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum RouteDirection {
    UNKNOWN(0, "UNKNOWN"),
    INBOUND(1, "INBOUND"),
    OUTBOUND(2, "OUTBOUND");

    @Getter
    private final int intValue;

    @Getter
    private final String strValue;

    private static final Map<String, RouteDirection> STR_TO_ENUM = new HashMap<>();
    static {
        STR_TO_ENUM.put(UNKNOWN.strValue, UNKNOWN);
        STR_TO_ENUM.put(INBOUND.strValue, INBOUND);
        STR_TO_ENUM.put(OUTBOUND.strValue, OUTBOUND);
    }

    public static RouteDirection of(final String value) {
        final RouteDirection routeDirection = STR_TO_ENUM.get(value);
        return routeDirection != null ? routeDirection : UNKNOWN;
    }

}
