package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.adapter;

import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementRepository;
import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.mapper.InventoryMovementPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.r2dbc.InventoryMovementR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.r2dbc.InventoryMovementR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryMovementRepositoryImpl implements InventoryMovementRepository {

    private final InventoryMovementR2dbcRepository inventoryMovementR2dbcRepository;
    private final InventoryMovementR2dbcEntityTemplate inventoryMovementR2dbcEntityTemplate;
    private final InventoryMovementPersistenceMapper inventoryMovementPersistenceMapper;

    @Override
    public Mono<InventoryMovement> insert(InventoryMovement inventoryMovement) {
        return inventoryMovementR2dbcEntityTemplate
                .insert(inventoryMovementPersistenceMapper.toInventoryMovementEntity(inventoryMovement))
                .map(inventoryMovementPersistenceMapper::toInventoryMovement);
    }

    @Override
    public Mono<InventoryMovement> update(InventoryMovement inventoryMovement) {
        return inventoryMovementR2dbcRepository
                .save(inventoryMovementPersistenceMapper.toInventoryMovementEntity(inventoryMovement))
                .map(inventoryMovementPersistenceMapper::toInventoryMovement);
    }

    @Override
    public Mono<InventoryMovement> findById(InventoryMovementId inventoryMovementId) {
        return inventoryMovementR2dbcRepository
                .findById(inventoryMovementId.getValue())
                .map(inventoryMovementPersistenceMapper::toInventoryMovement);
    }

    @Override
    public Mono<Integer> deleteById(InventoryMovementId inventoryMovementId) {
        return inventoryMovementR2dbcRepository
                .deleteInventoryMovementById(inventoryMovementId.getValue());
    }
}
