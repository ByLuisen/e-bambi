package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DepartmentCrudRepository extends ReactiveCrudRepository<Department, UUID> {
}
