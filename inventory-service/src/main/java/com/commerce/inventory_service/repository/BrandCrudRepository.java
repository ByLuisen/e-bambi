package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.Brand;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface BrandCrudRepository extends ReactiveCrudRepository<Brand, UUID> {
}
