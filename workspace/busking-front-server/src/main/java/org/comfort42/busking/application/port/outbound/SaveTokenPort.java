package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Token;

public interface SaveTokenPort {
    void save(Token token);
}
