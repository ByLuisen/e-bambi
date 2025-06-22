package com.e.bambi.inventory.infrastructure.persistence.supplier.adapter;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.inventory.application.supplier.port.outbound.repository.SupplierQueryRepository;
import com.e.bambi.inventory.infrastructure.persistence.supplier.repository.jooq.SupplierJooqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierQueryRepositoryImpl implements SupplierQueryRepository {

    private final SupplierJooqRepository supplierJooqRepository;

    @Override
    public Mono<PaginatedResultResponse<SupplierResponse>> findAll(int size, int page) {
        return supplierJooqRepository.findAll(size, page);
    }
}
