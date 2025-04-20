package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.MovementType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MovementTypeCrudRepository extends ReactiveCrudRepository<MovementType, UUID> {
}
