package com.e.bambi.payment.infrastructure.persistence.outbox.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_outbox_events")
public class PaymentOutboxEventEntity {

    @Id
    private UUID id;
    private String aggregatetype;
    private String aggregateid;
    @Column("event_type")
    private String eventType;
    private String payload;
}
