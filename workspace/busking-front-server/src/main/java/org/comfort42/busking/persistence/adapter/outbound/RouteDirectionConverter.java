package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.AttributeConverter;
import org.comfort42.busking.application.domain.model.RouteDirection;

class RouteDirectionConverter implements AttributeConverter<RouteDirection, String> {

    @Override
    public String convertToDatabaseColumn(final RouteDirection attribute) {
        return attribute.getStrValue();
    }

    @Override
    public RouteDirection convertToEntityAttribute(final String dbData) {
        return RouteDirection.of(dbData);
    }

}
