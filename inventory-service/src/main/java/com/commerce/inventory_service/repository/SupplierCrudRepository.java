package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.Supplier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface SupplierCrudRepository extends ReactiveCrudRepository<Supplier, UUID> {
}
