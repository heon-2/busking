package org.comfort42.busking.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.comfort42.busking.application.domain.model.*;
import org.comfort42.busking.common.json.serializer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
class JsonConfig {

    @Bean
    SimpleModule domainModelSerializers() {
        return new SimpleModule()
                .addSerializer(User.UserId.class, new UserIdJsonSerializer())
                .addSerializer(Company.CompanyId.class, new CompanyIdJsonSerializer())
                .addSerializer(Route.RouteId.class, new RouteIdJsonSerializer())
                .addSerializer(Station.StationId.class, new StationIdJsonSerializer())
                .addSerializer(Report.ReportId.class, new ReportIdJsonSerializer());
    }

    @Bean
    ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
        final ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        return objectMapper
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .registerModule(domainModelSerializers());
    }

}
