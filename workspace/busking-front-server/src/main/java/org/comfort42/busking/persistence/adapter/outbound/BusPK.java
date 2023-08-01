package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;

@Embeddable
public class BusPK implements Serializable {
    @JoinColumn(name = "company_id")
    private Long companyId;

    @JoinColumn(name="bus_num")
    private Long busId;
}
