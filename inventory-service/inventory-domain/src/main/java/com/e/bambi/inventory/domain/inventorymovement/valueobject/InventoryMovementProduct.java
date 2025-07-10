package com.e.bambi.inventory.domain.inventorymovement.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

@Getter
public class InventoryMovementProduct {
    private final ProductId id;
    private final String sku;
    private final String name;

    public InventoryMovementProduct(ProductId id, String sku, String name) {
        this.id = id;
        this.sku = sku;
        this.name = name;
    }
}
