package com.e.bambi.inventory.application.inventorymovement.dto.query;

import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class InventoryMovementQuery extends Query<Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>>> {
    private final List<UUID> supplierIds;
    private final List<UUID> productIds;
    private final List<UUID> movementTypeIds;
    private final List<String> productSkus;
    private final String orderBy;
    private final Integer page;
    private final Integer size;
}
