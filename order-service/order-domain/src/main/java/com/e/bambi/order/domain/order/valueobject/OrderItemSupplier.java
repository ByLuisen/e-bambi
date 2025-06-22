package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.Getter;

@Getter
public class OrderItemSupplier {
    private final SupplierId supplierId;
    private final String name;

    public OrderItemSupplier(SupplierId supplierId, String name) {
        this.supplierId = supplierId;
        this.name = name;
    }
}
