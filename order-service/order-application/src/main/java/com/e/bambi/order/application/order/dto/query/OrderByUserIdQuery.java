package com.e.bambi.order.application.order.dto.query;

import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class OrderByUserIdQuery extends Query<Mono<PaginatedResultResponse<OrderSummaryReadResponse>>> {
    private final UserId userId;
    private final int page;
    private final Integer date;
}
