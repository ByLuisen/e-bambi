package com.e.bambi.order.domain.entity;

import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import lombok.Getter;

@Getter
public class OrderStatus extends BaseEntity<OrderStatusId> {

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    OrderStatus(OrderStatusId orderStatusId, String name) {
        super.setId(orderStatusId);
        this.name = name;
    }
}
