package com.e.bambi.payment.application.outbox.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PaymentOutboxEvent {
    private final UUID id;
    private final String aggregatetype;
    private final String aggregateid;
    private final String eventType;
    private final String payload;
}
