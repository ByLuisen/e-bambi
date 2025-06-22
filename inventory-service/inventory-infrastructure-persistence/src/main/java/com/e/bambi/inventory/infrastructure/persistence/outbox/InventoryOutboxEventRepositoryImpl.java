package com.e.bambi.inventory.infrastructure.persistence.outbox;

import com.e.bambi.inventory.application.outbox.model.InventoryOutboxEvent;
import com.e.bambi.inventory.application.outbox.port.outbound.repository.InventoryOutboxEventRepository;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.jooq.InventoryMovementJooqRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.Require;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryOutboxEventRepositoryImpl implements InventoryOutboxEventRepository {
    @Override
    public Mono<InventoryOutboxEvent> insert(InventoryOutboxEvent inventoryOutboxEvent) {
        return null;
    }

    @Override
    public Mono<Integer> deleteById(UUID inventoryOutboxEventId) {
        return null;
    }
}
