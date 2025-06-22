package com.e.bambi.order.application.order.dto.query;

import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderQuery extends Query<Mono<PaginatedResultResponse<OrderSummaryReadResponse>>> {
    private final List<UUID> paymentMethodId;
    private final List<UUID> userId;
    private final List<OffsetDateTime> createdAt;
    private final List<BigDecimal> totalPrice;
    private final String orderBy;
    private final int page;
}
