package com.e.bambi.order.application.order.port.outbound.repository;

import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import reactor.core.publisher.Mono;

public interface OrderQueryRepository {
    Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findByUserId(UserId userId, int page, Integer date);

    Mono<OrderWithDetailReadResponse> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findAll(OrderQuery orderQuery);

    Mono<OrderWithDetailReadResponse> findByOrderId(OrderId orderId);

    Mono<TrackOrderReadResponse> trackOrder(UserId userId, OrderId orderId);
}
