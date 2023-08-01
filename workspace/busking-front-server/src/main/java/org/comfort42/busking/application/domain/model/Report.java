package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class Report {

    private final ReportId id;

    private final User reporter;

    private final String description;

    private final LocalDateTime created_at;

    private final Double lat;

    private final Double lng;

    private final Bus bus;

    @Value
    public static class ReportId{
        private final Long value;
    }

    public static Report withId(ReportId reportId,
                                User user,
                                String description,
                                LocalDateTime created_at,
                                Double lat,
                                Double lng,
                                Bus bus
    ) {
        return new Report(reportId,user,description,created_at,lat,lng,bus);
    }
}
