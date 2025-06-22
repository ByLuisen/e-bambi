package com.e.bambi.inventory.application.outbox.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class InventoryOutboxEvent {
    private final UUID id;
    private final String aggregatetype;
    private final String aggregateid;
    private final String eventType;
    private final String payload;
}
