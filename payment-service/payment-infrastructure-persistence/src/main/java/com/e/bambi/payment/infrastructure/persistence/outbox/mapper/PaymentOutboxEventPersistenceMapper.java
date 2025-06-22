package com.e.bambi.payment.infrastructure.persistence.outbox.mapper;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import com.e.bambi.payment.infrastructure.persistence.outbox.entity.PaymentOutboxEventEntity;
import io.r2dbc.spi.Readable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentOutboxEventPersistenceMapper {

    public PaymentOutboxEventEntity toPaymentOutboxEventEntity(PaymentOutboxEvent paymentOutboxEvent) {
        return PaymentOutboxEventEntity.builder()
                .id(paymentOutboxEvent.getId())
                .aggregatetype(paymentOutboxEvent.getAggregatetype())
                .aggregateid(paymentOutboxEvent.getAggregateid())
                .eventType(paymentOutboxEvent.getEventType())
                .payload(paymentOutboxEvent.getPayload())
                .build();
    }

    public PaymentOutboxEventEntity rowToPaymentOutboxEventEntity(Readable row) {
        return new PaymentOutboxEventEntity(
                row.get("id", UUID.class),
                row.get("aggregatetype", String.class),
                row.get("aggregateid", String.class),
                row.get("event_type", String.class),
                row.get("payload", String.class)
        );
    }

    public PaymentOutboxEvent toPaymentOutboxEvent(PaymentOutboxEventEntity paymentOutboxEventEntity) {
        return PaymentOutboxEvent.builder()
                .id(paymentOutboxEventEntity.getId())
                .aggregatetype(paymentOutboxEventEntity.getAggregatetype())
                .aggregateid(paymentOutboxEventEntity.getAggregateid())
                .eventType(paymentOutboxEventEntity.getEventType())
                .payload(paymentOutboxEventEntity.getPayload())
                .build();
    }

}
