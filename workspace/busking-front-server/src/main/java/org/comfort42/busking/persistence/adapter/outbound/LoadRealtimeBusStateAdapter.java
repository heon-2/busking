package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.comfort42.busking.application.port.outbound.LoadRealtimeBusStatePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
class LoadRealtimeBusStateAdapter implements LoadRealtimeBusStatePort {

    private final static RealtimeBusStateMapper realtimeBusStateMapper = RealtimeBusStateMapper.getInstance();
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
    public RealtimeBusState loadRealtimeBusState(final Bus.BusId busId) {
        try {
            final var key = String.format("bus:%s:%d", busId.companyId().value(), busId.no());
            return realtimeBusStateMapper.mapToDomainEntity(redisTemplate.opsForHash().multiGet(key, REDIS_FIELDS));
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public List<Pair<String, RealtimeBusState>> loadAllRealtimeBusState(final Company.CompanyId companyId) {
        return redisTemplate
                .opsForSet()
                .members("company:" + companyId.toString())
                .stream()
                .map(busId -> {
                    try {
                        final var resultSet = redisTemplate.opsForHash().multiGet(busId, REDIS_FIELDS);
                        return Pair.of(busId, realtimeBusStateMapper.mapToDomainEntity(resultSet));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(v -> v != null)
                .toList();
    }

}
