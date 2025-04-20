package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.dto.InventoryMovementFilterInputDTO;
import com.e.bambi.inventory.service.dto.PaginatedResultOutputDTO;
import reactor.core.publisher.Mono;

public interface InventoryMovementRepositoryCustom {
    Mono<PaginatedResultOutputDTO> findInventoriesWithPaginationAndFilters(InventoryMovementFilterInputDTO filters);
}
