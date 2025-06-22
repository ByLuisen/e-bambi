package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.infrastructure.persistence.order.repository.jooq.OrderJooqRepository;
import com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc.OrderR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final OrderJooqRepository orderJooqRepository;
    private final OrderR2dbcRepository orderR2dbcRepository;

    @Override
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findByUserId(UserId userId, int page, Integer date) {
        return orderJooqRepository.findByUserId(userId.getValue(), page, date);
    }

    @Override
    public Mono<OrderWithDetailReadResponse> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderJooqRepository.findByUserIdAndOrderId(userId.getValue(), orderId.getValue());
    }

    @Override
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findAll(OrderQuery orderQuery) {
        return orderJooqRepository.findAll(orderQuery);
    }

    @Override
    public Mono<OrderWithDetailReadResponse> findByOrderId(OrderId orderId) {
        return orderJooqRepository.findByOrderId(orderId.getValue());
    }

    @Override
    public Mono<TrackOrderReadResponse> trackOrder(UserId userId, OrderId orderId) {
        return orderR2dbcRepository.trackOrder(userId.getValue(), orderId.getValue());
    }
}
