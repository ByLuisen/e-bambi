package com.e.bambi.inventory.infrastructure.persistence.outbox.mapper;

import com.e.bambi.inventory.application.outbox.model.InventoryOutboxEvent;
import com.e.bambi.inventory.infrastructure.persistence.outbox.entity.InventoryOutboxEventEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryOutboxEventPersistenceMapper {

    public InventoryOutboxEventEntity toInventoryOutboxEventEntity(InventoryOutboxEvent outboxEvent) {
        return new InventoryOutboxEventEntity(
                outboxEvent.getId(),
                outboxEvent.getAggregatetype(),
                outboxEvent.getAggregateid(),
                outboxEvent.getEventType(),
                outboxEvent.getPayload()
        );
    }

    public InventoryOutboxEvent toInventoryOutboxEvent(InventoryOutboxEventEntity entity) {
        return new InventoryOutboxEvent(
                entity.getId(),
                entity.getAggregatetype(),
                entity.getAggregateid(),
                entity.getEventType(),
                entity.getPayload()
        );
    }
}
