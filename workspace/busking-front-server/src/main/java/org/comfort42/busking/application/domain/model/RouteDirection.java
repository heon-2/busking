package org.comfort42.busking.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum RouteDirection {
    UNKNOWN(0, "unknown"),
    INBOUND(1, "inbound"),
    OUTBOUND(2, "outbound");

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
        final RouteDirection routeDirection = STR_TO_ENUM.get(value.toLowerCase());
        return routeDirection != null ? routeDirection : UNKNOWN;
    }

}
