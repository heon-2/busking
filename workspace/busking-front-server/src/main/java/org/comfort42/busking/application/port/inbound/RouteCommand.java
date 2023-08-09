package org.comfort42.busking.application.port.inbound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class RouteCommand {
    private final Route.RouteId routeId;

    private final String name;

    private final List<Station.StationId> stationIds;

    private final Company.CompanyId companyId;

    private final String geometry;
}
