package com.e.bambi.inventory.application.inventorymovement.port.outbond.repository;

import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import reactor.core.publisher.Mono;

public interface InventoryMovementRepository {
    Mono<InventoryMovement> insert(InventoryMovement inventoryMovement);

    Mono<InventoryMovement> update(InventoryMovement inventoryMovement);

    Mono<InventoryMovement> findById(InventoryMovementId inventoryMovementId);

    Mono<Integer> deleteById(InventoryMovementId inventoryMovementId);

}
