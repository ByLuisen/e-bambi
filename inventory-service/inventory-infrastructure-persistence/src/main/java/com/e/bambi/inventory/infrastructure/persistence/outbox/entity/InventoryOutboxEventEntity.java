package com.e.bambi.inventory.infrastructure.persistence.outbox.entity;

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
@Table("inventory_outbox_events")
public class InventoryOutboxEventEntity {

    @Id
    private UUID id;
    private String aggregatetype;
    private String aggregateid;
    @Column("event_type")
    private String eventType;
    private String payload;
}
