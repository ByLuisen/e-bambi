package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.MovementType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MovementTypeCrudRepository extends ReactiveCrudRepository<MovementType, UUID> {
}
