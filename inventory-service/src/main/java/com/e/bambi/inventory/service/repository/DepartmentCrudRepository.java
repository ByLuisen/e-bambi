package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DepartmentCrudRepository extends ReactiveCrudRepository<Department, UUID> {
}
