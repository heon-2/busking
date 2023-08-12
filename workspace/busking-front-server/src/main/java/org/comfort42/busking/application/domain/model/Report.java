package org.comfort42.busking.application.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;


import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class Report {

    private final ReportId id;

    private final User reporter;

    private final String description;

    private final LocalDateTime createdAt;

    private final Double lng;

    private final Double lat;

    private final Bus bus;

    @Value
    public static class ReportId{
        private final Long value;

        @Override
        public String toString() {return value.toString();}
    }

    public static Report withId(ReportId reportId,
                                User user,
                                String description,
                                LocalDateTime created_at,
                                Double lng,
                                Double lat,
                                Bus bus
    ) {
        return new Report(reportId,user,description,created_at,lng,lat,bus);
    }
}
