package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.Brand;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface BrandCrudRepository extends ReactiveCrudRepository<Brand, UUID> {
}
