package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.InventoryMovement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface InventoryMovementCrudRepository extends ReactiveCrudRepository<InventoryMovement, UUID> {
}
