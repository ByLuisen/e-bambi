package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.Supplier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface SupplierCrudRepository extends ReactiveCrudRepository<Supplier, UUID> {
}
