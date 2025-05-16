package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.dto.PaginatedResultOutputDTO;
import com.commerce.inventory_service.dto.ProductFilterInputDTO;
import com.commerce.inventory_service.dto.ProductOutputDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepositoryCustom {
    Mono<PaginatedResultOutputDTO> findProductsWithPaginationAndFilters(ProductFilterInputDTO filters);
    Mono<ProductOutputDTO> findProductById(UUID productId);
}
