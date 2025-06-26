package com.e.bambi.inventory.infrastructure.persistence.outbox.adapter;

import com.e.bambi.inventory.application.outbox.model.InventoryOutboxEvent;
import com.e.bambi.inventory.application.outbox.port.outbound.repository.InventoryOutboxEventRepository;
import com.e.bambi.inventory.infrastructure.persistence.outbox.mapper.InventoryOutboxEventPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.outbox.repository.InventoryOutboxEventR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.outbox.repository.InventoryOutboxEventR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InventoryOutboxEventRepositoryImpl implements InventoryOutboxEventRepository {

    private final InventoryOutboxEventR2dbcEntityTemplate inventoryOutboxEventR2dbcEntityTemplate;
    private final InventoryOutboxEventR2dbcRepository inventoryOutboxEventR2dbcRepository;
    private final InventoryOutboxEventPersistenceMapper inventoryOutboxEventPersistenceMapper;

    @Override
    public Mono<InventoryOutboxEvent> insert(InventoryOutboxEvent inventoryOutboxEvent) {
        return inventoryOutboxEventR2dbcEntityTemplate
                .insert(inventoryOutboxEventPersistenceMapper.toInventoryOutboxEventEntity(inventoryOutboxEvent))
                .map(inventoryOutboxEventPersistenceMapper::toInventoryOutboxEvent);
    }

    @Override
    public Mono<Boolean> existsByAggregateIdAndAggregateTypeIn(String aggregateid, String... aggregatetype) {
        return inventoryOutboxEventR2dbcRepository
                .existsByAggregateidAndAggregatetypeIn(aggregateid, Arrays.asList(aggregatetype));
    }
}
