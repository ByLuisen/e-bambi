package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class InventoryMovementProductResponse {
    private final UUID id;
    private final String sku;
    private final String name;
}
