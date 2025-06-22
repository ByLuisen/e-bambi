package com.e.bambi.inventory.application.supplier.port.outbound.repository;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import reactor.core.publisher.Mono;

public interface SupplierQueryRepository {
    Mono<PaginatedResultResponse<SupplierResponse>> findAll(int size, int page);
}
