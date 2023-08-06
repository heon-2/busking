package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationMapper {

    private static final CompanyMapper companyMapper=CompanyMapper.getInstance();

    Station mapToDomainEntity(
            StationJpaEntity station
    ) {
        return Station.withId(
                new Station.StationId(station.getId()),
                station.getName(),
                station.getLng(),
                station.getLng(),
                companyMapper.mapToDomainEntity(station.getCompany())
        );
    }
}
