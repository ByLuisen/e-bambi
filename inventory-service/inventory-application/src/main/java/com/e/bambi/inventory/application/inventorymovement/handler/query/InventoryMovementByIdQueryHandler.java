package com.e.bambi.inventory.application.inventorymovement.handler.query;

import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementByIdQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementQueryRepository;
import com.e.bambi.inventory.domain.exception.InventoryMovementNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryMovementByIdQueryHandler implements
        QueryHandler<Mono<InventoryMovementSummaryReadResponse>, InventoryMovementByIdQuery> {

    private final InventoryMovementQueryRepository inventoryMovementQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<InventoryMovementSummaryReadResponse> handle(InventoryMovementByIdQuery query) {
        return inventoryMovementQueryRepository
                .findById(query.getInventoryMovementId())
                .switchIfEmpty(
                        Mono.error(new InventoryMovementNotFoundException("Inventory movement with id: " +
                                query.getInventoryMovementId().getValue() + " could not be found"))
                );
    }
}
