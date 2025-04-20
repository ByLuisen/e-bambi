package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.ProductStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProductStatusCrudRepository extends ReactiveCrudRepository<ProductStatus, UUID> {
}
