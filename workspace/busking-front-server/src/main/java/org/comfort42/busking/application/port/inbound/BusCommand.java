package org.comfort42.busking.application.port.inbound;

import lombok.*;
import org.comfort42.busking.web.adapter.inbound.LoadRouteController;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BusCommand {
    private final Long companyId;
    private final Long busNum;
    private final List<LoadRouteController.RoutePayload> routePayloads;
}
