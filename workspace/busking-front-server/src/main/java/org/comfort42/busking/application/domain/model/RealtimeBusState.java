package org.comfort42.busking.application.domain.model;

public record RealtimeBusState(
        long at,
        double rawLatitude,
        double rawLongitude,
        long adjustedAt,
        double adjustedLatitude,
        double adjustedLongitude) {
}
