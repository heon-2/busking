package org.comfort42.busking.application.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Token(
        TokenId tokenId,
        User.UserId subject,
        Instant issuedAt,
        Instant accessExpiresAt,
        Instant refreshExpiresAt) {
    public static class TokenId {
        private final UUID value;

        private TokenId(final UUID value) {
            this.value = value;
        }

        public static TokenId of(final UUID value) {
            return new TokenId(value);
        }

        public static TokenId of(final String value) {
            return new TokenId(UUID.fromString(value));
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof TokenId && value.equals(((TokenId) other).value);
        }
    }
}
