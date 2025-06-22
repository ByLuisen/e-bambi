package com.e.bambi.inventory.application.inventorymovement.port.outbond.repository;

import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import reactor.core.publisher.Mono;

public interface InventoryMovementQueryRepository {

    Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>> searchInventoryMovements(InventoryMovementQuery
                                                                                                         query);

    Mono<InventoryMovementSummaryReadResponse> findById(InventoryMovementId inventoryMovementId);
}
