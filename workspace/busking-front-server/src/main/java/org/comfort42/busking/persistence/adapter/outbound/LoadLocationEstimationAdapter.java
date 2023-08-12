package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.LocationEstimation;
import org.comfort42.busking.application.port.outbound.LoadLocationEstimationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
class LoadLocationEstimationAdapter implements LoadLocationEstimationPort {

    private final static List<Object> LAT_AND_LNG = new ArrayList<>();

    static {
        LAT_AND_LNG.add("lat");
        LAT_AND_LNG.add("lng");
    }

    @Autowired
    @Qualifier("buskingGpsRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LocationEstimation loadLocationEstimation(final Company.CompanyId companyId, final long busNo) {
        final var busId = String.format("bus:%s:%d", companyId, busNo);

        final var values = redisTemplate.opsForHash().multiGet(busId, LAT_AND_LNG);
        final var latitude = (String) values.get(0) ;
        final var longitude = (String) values.get(1) ;
        if (latitude == null || longitude == null) {
            throw new NoSuchElementException();
        }

        return new LocationEstimation(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}
