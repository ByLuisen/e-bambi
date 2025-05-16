package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.ProductStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductStatusCrudRepository extends ReactiveCrudRepository<ProductStatus, UUID> {
}
