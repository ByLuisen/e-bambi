package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class InventoryMovementSummarySupplier {

    private final UUID id;
    private final String name;
}
