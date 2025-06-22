package com.e.bambi.order.infrastructure.persistence.outbox.entity;

import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("order_outbox_events")
public class OrderOutboxEventEntity {

    @Id
    private UUID id;
    private String aggregatetype;
    private String aggregateid;
    @Column("event_type")
    private String eventType;
    @Column("saga_status")
    private SagaStatus sagaStatus;
    private String payload;
}
