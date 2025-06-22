package com.e.bambi.inventory.infrastructure.persistence.department.adapter;

import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentRepository;
import com.e.bambi.inventory.domain.department.entity.Department;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.persistence.department.mapper.DepartmentPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.department.repository.DepartmentR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.department.repository.DepartmentR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentR2dbcEntityTemplate departmentR2dbcEntityTemplate;
    private final DepartmentR2dbcRepository departmentR2dbcRepository;
    private final DepartmentPersistenceMapper departmentPersistenceMapper;

    @Override
    public Mono<Department> insert(Department department) {
        return departmentR2dbcEntityTemplate
                .insert(departmentPersistenceMapper.toDepartmentEntity(department))
                .map(departmentPersistenceMapper::toDepartment);
    }

    @Override
    public Mono<Department> update(Department department) {
        return departmentR2dbcRepository
                .save(departmentPersistenceMapper.toDepartmentEntity(department))
                .map(departmentPersistenceMapper::toDepartment);
    }

    @Override
    public Mono<Integer> deleteById(DepartmentId departmentId) {
        return departmentR2dbcRepository
                .deleteDepartmentById(departmentId.getValue());
    }

}
