package com.e.bambi.inventory.application.supplier.handler.query;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.query.SupplierQuery;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.inventory.application.supplier.port.outbound.repository.SupplierQueryRepository;
import com.e.bambi.inventory.domain.exception.SupplierNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierQueryHandler implements QueryHandler<Mono<PaginatedResultResponse<SupplierResponse>>, SupplierQuery> {

    private final SupplierQueryRepository supplierQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<SupplierResponse>> handle(SupplierQuery query) {
        return supplierQueryRepository.findAll(query.getSize(), query.getPage())
                .switchIfEmpty(Mono.error(new SupplierNotFoundException("No Suppliers could be found")));
    }
}
