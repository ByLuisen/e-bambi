package com.e.bambi.inventory.infrastructure.persistence.department.adapter;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentQueryRepository;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.persistence.department.repository.DepartmentR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DepartmentQueryRepositoryImpl implements DepartmentQueryRepository {

    private final DepartmentR2dbcRepository departmentR2dbcRepository;

    @Override
    public Flux<DepartmentResponse> findAll() {
        return departmentR2dbcRepository
                .departmentFindAll();
    }

    @Override
    public Mono<Boolean> existsById(DepartmentId departmentId) {
        return departmentR2dbcRepository
                .existsById(departmentId.getValue());
    }
}
