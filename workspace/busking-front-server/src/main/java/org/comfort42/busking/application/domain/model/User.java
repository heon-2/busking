package org.comfort42.busking.application.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.comfort42.busking.application.domain.serializer.UserIdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

public record User(
        @JsonSerialize(using = UserIdSerializer.class)
        UserId id,
        String password,
        String name,
        String email,
        String phone,
        Company.CompanyId companyId,
        UserRole role
) {

    public static class UserId {
        private final String value;

        private UserId(final String value) {
            this.value = value;
        }

        public static UserId of(final String value) {
            return new UserId(value);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
