package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.entity.InventoryMovementEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface InventoryMovementR2dbcRepository extends R2dbcRepository<InventoryMovementEntity, UUID> {

    @Modifying
    @Query("DELETE FROM inventory_movements WHERE id = :inventoryMovementId")
    Mono<Integer> deleteInventoryMovementById(UUID inventoryMovementId);
}
