package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.AttributeConverter;
import org.comfort42.busking.application.domain.model.UserRole;

class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public UserRole convertToEntityAttribute(final String entityAttribute) {
        return UserRole.of(entityAttribute);
    }

    @Override
    public String convertToDatabaseColumn(final UserRole attribute) {
        return attribute.value();
    }

}
