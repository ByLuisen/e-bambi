package com.e.bambi.inventory.application.department.handler.query;

import com.e.bambi.inventory.application.department.dto.query.DepartmentFindAllQuery;
import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentQueryRepository;
import com.e.bambi.inventory.domain.exception.DepartmentNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DepartmentFindAllQueryHandler implements QueryHandler<Flux<DepartmentResponse>, DepartmentFindAllQuery> {

    private final DepartmentQueryRepository departmentQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<DepartmentResponse> handle(DepartmentFindAllQuery query) {
        return departmentQueryRepository.findAll()
                .switchIfEmpty(
                        Mono.error(new DepartmentNotFoundException("No Department could be found"))
                );
    }
}
