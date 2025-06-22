package com.e.bambi.inventory.domain.inventorymovement.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class InventoryMovementId extends BaseId<UUID> {
    public InventoryMovementId(UUID value) {
        super(value);
    }
}
