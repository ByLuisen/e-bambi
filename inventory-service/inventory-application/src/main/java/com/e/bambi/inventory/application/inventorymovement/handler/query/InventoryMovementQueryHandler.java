package com.e.bambi.inventory.application.inventorymovement.handler.query;

import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.exception.InventoryMovementNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryMovementQueryHandler implements
        QueryHandler<Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>>, InventoryMovementQuery> {

    private final InventoryMovementQueryRepository inventoryMovementQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>> handle(InventoryMovementQuery query) {
        return inventoryMovementQueryRepository.searchInventoryMovements(query)
                .switchIfEmpty(Mono.error(new InventoryMovementNotFoundException("No inventory movements " +
                        "could be found")));
    }
}
