package com.e.bambi.inventory.application.inventorymovement.dto.command;

import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateInventoryMovementProduct {
    private final ProductId id;
    private final String sku;
    private final String name;
}
