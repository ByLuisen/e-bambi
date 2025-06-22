package com.e.bambi.inventory.application.supplier.dto.query;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class SupplierQuery extends Query<Mono<PaginatedResultResponse<SupplierResponse>>> {
    private final int size;
    private final int page;
}
