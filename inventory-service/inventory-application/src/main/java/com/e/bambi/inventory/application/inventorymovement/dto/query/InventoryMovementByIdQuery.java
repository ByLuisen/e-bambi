package com.e.bambi.inventory.application.inventorymovement.dto.query;

import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class InventoryMovementByIdQuery extends Query<Mono<InventoryMovementSummaryReadResponse>> {
    private final InventoryMovementId inventoryMovementId;
}
