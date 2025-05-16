package com.e.bambi.payment.application.outbox.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PaymentOutboxEvent {
    private UUID id;
    private String aggregatetype;
    private String aggregateid;
    private String eventType;
    private String payload;
}
