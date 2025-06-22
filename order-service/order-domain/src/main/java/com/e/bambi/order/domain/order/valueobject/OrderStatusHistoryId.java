package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderStatusHistoryId extends BaseId<UUID> {

    public OrderStatusHistoryId(UUID value) {
        super(value);
    }
}
