package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;

import java.util.Optional;

public interface LoadTokenPort {
    Optional<Token> loadToken(final Token.TokenSubject userId);
}
