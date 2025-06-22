package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class InventoryMovementSummaryProduct {

    private final UUID id;
    private final String sku;
    private final String name;
}
