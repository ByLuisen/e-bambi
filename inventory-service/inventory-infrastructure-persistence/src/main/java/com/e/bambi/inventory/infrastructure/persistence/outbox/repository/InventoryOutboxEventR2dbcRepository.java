package com.e.bambi.inventory.infrastructure.persistence.outbox.repository;

import com.e.bambi.inventory.infrastructure.persistence.outbox.entity.InventoryOutboxEventEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryOutboxEventR2dbcRepository extends R2dbcRepository<InventoryOutboxEventEntity, UUID> {

    Mono<Boolean> existsByAggregateidAndAggregatetypeIn(String aggregateid, List<String> aggregatetype);
}
