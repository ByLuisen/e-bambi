package com.e.bambi.order.infrastructure.persistence.outbox.mapper;

import com.e.bambi.order.application.outbox.model.OrderOutboxEvent;
import com.e.bambi.order.infrastructure.persistence.outbox.entity.OrderOutboxEventEntity;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import io.r2dbc.spi.Readable;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public OrderOutboxEventEntity rowToOrderOutboxEventEntity(Readable r) {
        return new OrderOutboxEventEntity(
                r.get("id", UUID.class),
                r.get("aggregatetype", String.class),
                r.get("aggregateid", String.class),
                r.get("event_type", String.class),
                SagaStatus.valueOf(r.get("saga_status", String.class)),
                r.get("payload", String.class)
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
