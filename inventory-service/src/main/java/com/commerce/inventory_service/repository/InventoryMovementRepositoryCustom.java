package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.dto.InventoryMovementFilterInputDTO;
import com.commerce.inventory_service.dto.PaginatedResultOutputDTO;
import reactor.core.publisher.Mono;

public interface InventoryMovementRepositoryCustom {
    Mono<PaginatedResultOutputDTO> findInventoriesWithPaginationAndFilters(InventoryMovementFilterInputDTO filters);
}
