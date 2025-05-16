package com.e.bambi.order.domain.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderStatusHistoryId extends BaseId<UUID> {

    protected OrderStatusHistoryId(UUID value) {
        super(value);
    }
}
