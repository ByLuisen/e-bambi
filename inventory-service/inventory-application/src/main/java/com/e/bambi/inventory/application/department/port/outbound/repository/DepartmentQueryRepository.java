package com.e.bambi.inventory.application.department.port.outbound.repository;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentQueryRepository {

    Flux<DepartmentResponse> findAll();

    Mono<Boolean> existsById(DepartmentId departmentId);

}
