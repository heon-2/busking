package org.comfort42.busking.application.port.inbound;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class BusCommand {
    private final Long companyId;
    private final Long busNum;
}
