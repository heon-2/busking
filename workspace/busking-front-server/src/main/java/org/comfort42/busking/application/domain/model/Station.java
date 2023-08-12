package org.comfort42.busking.application.domain.model;

public record Station(
        StationId id,
        String name,
        double lng,
        double lat,
        Company company) {

    public record StationId(long value) {
        @Override
        public String toString() {
            return Long.toString(value);
        }

        public static StationId of(final long value) {
            return new StationId(value);
        }
    }
}
