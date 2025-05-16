package com.e.bambi.order.application.handler.query;

import com.e.bambi.order.application.dto.query.OrderByUserIdAndOrderIdQuery;
import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByUserIdAndOrderIdQueryHandler implements QueryHandler<Mono<OrderResponse>, OrderByUserIdAndOrderIdQuery> {

    private final OrderRepository orderRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    @Override
    public Mono<OrderResponse> handle(OrderByUserIdAndOrderIdQuery query) {
        UserId userId = query.userId();

        return orderRepository
                .findByUserIdAndOrderId(userId, query.orderId())
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Order with user id: " + userId.getValue() +
                                " could not be found"))
                )
                .map(orderApplicationMapper::toOrderResponse);
    }
}
