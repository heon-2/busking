package org.comfort42.busking.application.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

public record Token(
        TokenId tokenId,
        TokenSubject subject,
        Instant issuedAt,
        Instant accessExpiresAt,
        Instant refreshExpiresAt) {

    public record TokenId(UUID value) {
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

    public record TokenSubject(User.UserId userId, Company.CompanyId companyId) {
        public static TokenSubject of(final String tokenSubject) {
            final int delimIndex = tokenSubject.indexOf(':');
            if (delimIndex == -1) {
                // TODO(meo-s): empty exception message
                throw new IllegalArgumentException();
            }

            final String userId = tokenSubject.substring(delimIndex + 1);
            final String companyId = tokenSubject.substring(0, delimIndex);
            return new TokenSubject(User.UserId.of(userId), Company.CompanyId.of(companyId));
        }

        @Override
        public String toString() {
            return companyId.toString() + ':' + userId.toString();
        }
    }

}
