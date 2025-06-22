package com.e.bambi.inventory.application.product.dto.query;

import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@ToString
@Getter
@RequiredArgsConstructor
public class ProductQuery extends Query<Mono<PaginatedResultResponse<ProductSummaryReadResponse>>> {
    private final List<UUID> brandIds;
    private final UUID departmentId;
    private final List<UUID> productStatusIds;
    private final List<String> skus;
    private final String orderBy;
    private final int page;
    private final int size;
}
