package com.e.bambi.inventory.application.outbox.port.outbound.repository;

import com.e.bambi.inventory.application.outbox.model.InventoryOutboxEvent;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface InventoryOutboxEventRepository {

    Mono<InventoryOutboxEvent> insert(InventoryOutboxEvent inventoryOutboxEvent);

    Mono<Integer> deleteById(UUID inventoryOutboxEventId);
}
