package com.e.bambi.inventory.application.outbox;

import com.e.bambi.inventory.application.outbox.model.InventoryOutboxEvent;
import com.e.bambi.inventory.application.outbox.port.outbound.repository.InventoryOutboxEventRepository;
import com.e.bambi.inventory.domain.exception.InventoryDomainException;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryOutboxEventHelper {

    private final ObjectMapper objectMapper;
    private final InventoryOutboxEventRepository inventoryOutboxEventRepository;

    public Mono<Void> saveInventoryOutboxEvent(String aggregatetype, String aggregateid, @Nullable String eventype,
                                               InventoryEventPayload payload) {
        return save(new InventoryOutboxEvent(
                UUID.randomUUID(),
                aggregatetype,
                aggregateid,
                eventype,
                createPayload(payload)
        ));
    }

    public Mono<Boolean> existsInventoryOutboxEventByAggregateidAndAggregateType(String aggregateid,
                                                                               String... aggregatetype) {
        return inventoryOutboxEventRepository
                .existsByAggregateIdAndAggregateTypeIn(aggregateid, aggregatetype);
    }

    private Mono<Void> save(InventoryOutboxEvent inventoryOutboxEvent) {
        return inventoryOutboxEventRepository.insert(inventoryOutboxEvent)
                .doOnError(error ->
                        Mono.error(new InventoryDomainException("Could not be save InventoryOutboxEvent with " +
                                "aggregateid: " + inventoryOutboxEvent.getAggregateid() + " and payload: " +
                                inventoryOutboxEvent.getPayload()))
                ).doOnSuccess(saved ->
                        log.info("InventoryOutboxEvent is saved with id: {}", inventoryOutboxEvent.getId())
                ).then();
    }

    private String createPayload(InventoryEventPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new InventoryDomainException("Could not create InventoryEventPayload json!", e);
        }
    }
}
