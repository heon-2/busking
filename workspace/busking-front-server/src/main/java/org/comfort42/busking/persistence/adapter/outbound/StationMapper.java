package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StationMapper {

    private static StationMapper instance = null;
    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();

    public static StationMapper getInstance() {
        if (instance == null) {
            instance = new StationMapper();
        }
        return instance;
    }

    Station mapToDomainEntity(final StationJpaEntity station) {
        return new Station(
                Station.StationId.of(station.getId()),
                station.getName(),
                station.getLng(),
                station.getLat(),
                companyMapper.mapToDomainEntity(station.getCompany())
        );
    }

    StationJpaEntity mapToJpaEntity(final Station station) {
        return new StationJpaEntity(
                station.id().value(),
                station.name(),
                station.lng(),
                station.lat(),
                companyMapper.mapToJpaEntity(station.company())
        );
    }

}
