package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

@Getter
public class OrderItemProduct {
    private final ProductId productId;
    private final String sku;
    private final String name;

    public OrderItemProduct(ProductId productId, String sku, String name) {
        this.productId = productId;
        this.sku = sku;
        this.name = name;
    }
}
