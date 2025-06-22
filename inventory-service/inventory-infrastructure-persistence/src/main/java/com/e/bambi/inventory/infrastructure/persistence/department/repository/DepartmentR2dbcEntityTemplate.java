package com.e.bambi.inventory.infrastructure.persistence.department.repository;

import com.e.bambi.inventory.infrastructure.persistence.department.entity.DepartmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DepartmentR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<DepartmentEntity> insert(DepartmentEntity departmentEntity) {
        return r2dbcEntityTemplate.insert(departmentEntity);
    }
}
