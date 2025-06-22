package com.e.bambi.inventory.infrastructure.persistence.movementtype.repository;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.entity.MovementTypeEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MovementTypeR2dbcRepository extends R2dbcRepository<MovementTypeEntity, UUID> {

    @Modifying
    @Query("DELETE FROM movement_types WHERE id = :movementTypeId")
    Mono<Integer> deleteMovementTypeById(UUID movementTypeId);

    @Query("SELECT id, name, description FROM movement_types")
    Flux<MovementTypeResponse> movementTypeFindAll();
}
