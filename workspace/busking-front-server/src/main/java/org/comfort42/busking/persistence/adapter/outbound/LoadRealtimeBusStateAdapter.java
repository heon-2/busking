package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.comfort42.busking.application.port.outbound.LoadRealtimeBusStatePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
class LoadRealtimeBusStateAdapter implements LoadRealtimeBusStatePort {

    private final static RealtimeBusStateMapper realtimeBusStateMapper = RealtimeBusStateMapper.getInstance();

    @Autowired
    @Qualifier("buskingGpsRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    private final ValueOperations<String, String> redisValueOps;

    LoadRealtimeBusStateAdapter(@Qualifier("buskingGpsRedisTemplate") final RedisTemplate<String, String> redisTemplate) {
        redisValueOps = redisTemplate.opsForValue();
    }

    @Override
    public String loadRealtimeBusState(final Bus.BusId busId) {
        try {
            final var key = String.format("bus:%s:%d", busId.companyId().value(), busId.no());
            return redisTemplate.opsForValue().get(key);
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public List<Pair<String, String>> loadAllRealtimeBusState(final Company.CompanyId companyId) {
        return redisTemplate
                .opsForSet()
                .members("company:" + companyId.toString())
                .stream()
                .map(busId -> {
                    try {
                        return Pair.of(busId, Objects.requireNonNull(redisValueOps.get(busId)));
                    } catch (NullPointerException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

}
