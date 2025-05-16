package com.e.bambi.order.infrastructure.persistence.outbox.payment.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("payment_outbox")
public class PaymentOutboxEntity {

    @Id
    private UUID id;
    private String aggregatetype;
    private String aggregateid;
    private String
}
