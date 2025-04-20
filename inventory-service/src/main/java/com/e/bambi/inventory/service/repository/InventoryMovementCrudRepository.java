package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.InventoryMovement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface InventoryMovementCrudRepository extends ReactiveCrudRepository<InventoryMovement, UUID> {
}
