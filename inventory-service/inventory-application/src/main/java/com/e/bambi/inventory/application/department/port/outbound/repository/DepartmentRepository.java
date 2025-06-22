package com.e.bambi.inventory.application.department.port.outbound.repository;

import com.e.bambi.inventory.domain.department.entity.Department;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import reactor.core.publisher.Mono;

public interface DepartmentRepository {

    Mono<Department> insert(Department department);

    Mono<Department> update(Department department);

    Mono<Integer> deleteById(DepartmentId departmentId);

}
