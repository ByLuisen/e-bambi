package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class InventoryMovementSummarySupplier {
    private final UUID id;
    private final String name;
}
