package com.e.bambi.inventory.infrastructure.persistence.department.repository;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.infrastructure.persistence.department.entity.DepartmentEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface DepartmentR2dbcRepository extends R2dbcRepository<DepartmentEntity, UUID> {

    @Modifying
    @Query("DELETE FROM departments WHERE id = :departmentId")
    Mono<Integer> deleteDepartmentById(UUID departmentId);

    @Query("SELECT id, name FROM departments")
    Flux<DepartmentResponse> departmentFindAll();
}
