package org.comfort42.busking.application.port.inbound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;

@Getter
@Setter
@RequiredArgsConstructor
public class StationCommand {
    private final Station.StationId stationId;

    private final String name;

    private final Double lng;

    private final Double lat;

    private final Company.CompanyId companyId;
}
