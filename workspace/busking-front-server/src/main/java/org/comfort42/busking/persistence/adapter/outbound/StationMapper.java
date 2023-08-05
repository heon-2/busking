package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationMapper {

    private final CompanyMapper companyMapper;

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
