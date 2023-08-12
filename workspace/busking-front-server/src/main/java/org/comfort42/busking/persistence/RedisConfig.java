package org.comfort42.busking.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class RedisConfig {

    @Autowired
    ApplicationContext ctx;

    @Value("${busking.gps.redis.host}")
    String buskingGpsRedisHost;

    @Value("${busking.gps.redis.port}")
    int buskingGpsRedisPort;

    private RedisTemplate<String, String> createStringRedisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, String> tmpl = new RedisTemplate<>();
        tmpl.setConnectionFactory(redisConnectionFactory);
        tmpl.setKeySerializer(new StringRedisSerializer());
        tmpl.setValueSerializer(new StringRedisSerializer());
        tmpl.setHashKeySerializer(new StringRedisSerializer());
        tmpl.setHashValueSerializer(new StringRedisSerializer());
        return tmpl;
    }

    @Bean("buskingLoginRedisConnectionFactory")
    RedisConnectionFactory buskingLoginRedisConnectionFactory(
            @Value("${busking.login.redis.host}") String buskingLoginRedisHost,
            @Value("${busking.login.redis.port}") int buskingLoginRedisPort) {
        final String ENV_BUSKING_LOGIN_REDIS_HOST = System.getenv("BUSKING_LOGIN_REDIS_HOST");
        final String ENV_BUSKING_LOGIN_REDIS_PORT = System.getenv("BUSKING_LOGIN_REDIS_PORT");
        buskingLoginRedisHost = ENV_BUSKING_LOGIN_REDIS_HOST != null ? ENV_BUSKING_LOGIN_REDIS_HOST : buskingLoginRedisHost;
        buskingLoginRedisPort = ENV_BUSKING_LOGIN_REDIS_PORT != null ? Integer.parseInt(ENV_BUSKING_LOGIN_REDIS_PORT) : buskingLoginRedisPort;
        return new LettuceConnectionFactory(buskingLoginRedisHost, buskingLoginRedisPort);
    }

    @Bean("buskingGpsRedisConnectionFactory")
    RedisConnectionFactory buskingGpsRedisConnectionFactory(
            @Value("${busking.gps.redis.host}") String buskingGpsRedisHost,
            @Value("${busking.gps.redis.port}") int buskingGpsRedisPort) {
        final String ENV_BUSKING_GPS_REDIS_HOST = System.getenv("BUSKING_GPS_REDIS_HOST");
        final String ENV_BUSKING_GPS_REDIS_PORT = System.getenv("BUSKING_GPS_REDIS_PORT");
        buskingGpsRedisHost = ENV_BUSKING_GPS_REDIS_HOST != null ? ENV_BUSKING_GPS_REDIS_HOST : buskingGpsRedisHost;
        buskingGpsRedisPort = ENV_BUSKING_GPS_REDIS_PORT != null ? Integer.parseInt(ENV_BUSKING_GPS_REDIS_PORT) : buskingGpsRedisPort;
        return new LettuceConnectionFactory(buskingGpsRedisHost, buskingGpsRedisPort);
    }

    @Bean("redisTemplate")
    @Primary
    @Qualifier("buskingLoginRedisTemplate")
    RedisTemplate<String, String> buskingLoginRedisTemplate() {
        return createStringRedisTemplate(ctx.getBean("buskingLoginRedisConnectionFactory", RedisConnectionFactory.class));
    }

    @Bean
    @Qualifier("buskingGpsRedisTemplate")
    RedisTemplate<String, String> buskingGpsRedisTemplate() {
        return createStringRedisTemplate(ctx.getBean("buskingGpsRedisConnectionFactory", RedisConnectionFactory.class));
    }

}
