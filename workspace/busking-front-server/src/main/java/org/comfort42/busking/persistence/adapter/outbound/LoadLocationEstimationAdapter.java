package org.comfort42.busking.persistence.adapter.outbound;

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

    private final static List<Object> REDIS_FIELDS = new ArrayList<>();

    static {
        REDIS_FIELDS.add("at");
        REDIS_FIELDS.add("rawLat");
        REDIS_FIELDS.add("rawLng");
        REDIS_FIELDS.add("adjAt");
        REDIS_FIELDS.add("adjLat");
        REDIS_FIELDS.add("adjLng");
    }

    @Autowired
    @Qualifier("buskingGpsRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LocationEstimation loadLocationEstimation(final Company.CompanyId companyId, final long busNo) {
        final var busId = String.format("bus:%s:%d", companyId, busNo);

        final var values = redisTemplate.opsForHash().multiGet(busId, REDIS_FIELDS);
        return new LocationEstimation(
                Long.parseLong((String) values.get(0)),
                Double.parseDouble((String) values.get(1)),
                Double.parseDouble((String) values.get(2)),
                Long.parseLong((String) values.get(3)),
                Double.parseDouble((String) values.get(4)),
                Double.parseDouble((String) values.get(5))
        );
    }
}
