package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.dto.PaginatedResultOutputDTO;
import com.e.bambi.inventory.service.dto.ProductFilterInputDTO;
import com.e.bambi.inventory.service.dto.ProductOutputDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepositoryCustom {
    Mono<PaginatedResultOutputDTO> findProductsWithPaginationAndFilters(ProductFilterInputDTO filters);
    Mono<ProductOutputDTO> findProductById(UUID productId);
}
