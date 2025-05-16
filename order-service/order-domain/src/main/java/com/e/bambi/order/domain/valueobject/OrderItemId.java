package com.e.bambi.order.domain.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderItemId extends BaseId<UUID> {

    public OrderItemId(UUID value) {
        super(value);
    }
}
