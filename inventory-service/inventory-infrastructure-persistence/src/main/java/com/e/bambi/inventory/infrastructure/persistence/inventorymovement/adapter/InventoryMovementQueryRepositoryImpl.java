package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.adapter;

import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.jooq.InventoryMovementJooqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryMovementQueryRepositoryImpl implements InventoryMovementQueryRepository {

    private final InventoryMovementJooqRepository inventoryMovementJooqRepository;

    @Override
    public Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>> searchInventoryMovements(InventoryMovementQuery query) {
        return inventoryMovementJooqRepository.searchInventoryMovements(query);
    }

    @Override
    public Mono<InventoryMovementSummaryReadResponse> findById(InventoryMovementId inventoryMovementId) {
        return inventoryMovementJooqRepository.findById(inventoryMovementId.getValue());
    }
}
