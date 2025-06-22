package com.e.bambi.order.infrastructure.persistence.outbox.mapper;

import com.e.bambi.order.application.outbox.model.OrderOutboxEvent;
import com.e.bambi.order.infrastructure.persistence.outbox.entity.OrderOutboxEventEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderOutboxEventPersistenceMapper {

    public OrderOutboxEventEntity toOrderOutboxEventEntity(OrderOutboxEvent event) {
        return new OrderOutboxEventEntity(
                event.getId(),
                event.getAggregatetype(),
                event.getAggregateid(),
                event.getEventType(),
                event.getSagaStatus(),
                event.getPayload()
        );
    }

    public OrderOutboxEvent toOrderOutboxEvent(OrderOutboxEventEntity entity) {
        return OrderOutboxEvent.builder()
                .id(entity.getId())
                .aggregatetype(entity.getAggregatetype())
                .aggregateid(entity.getAggregateid())
                .eventType(entity.getEventType())
                .sagaStatus(entity.getSagaStatus())
                .payload(entity.getPayload())
                .build();
    }
}
