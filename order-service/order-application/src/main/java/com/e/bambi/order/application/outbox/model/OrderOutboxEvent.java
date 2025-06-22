package com.e.bambi.order.application.outbox.model;

import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderOutboxEvent {
    private final UUID id;
    private final String aggregatetype;
    private final String aggregateid;
    private final String eventType;
    private final SagaStatus sagaStatus;
    private final String payload;
}
