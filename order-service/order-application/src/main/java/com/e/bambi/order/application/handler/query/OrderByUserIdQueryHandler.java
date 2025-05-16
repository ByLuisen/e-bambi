package com.e.bambi.order.application.handler.query;

import com.e.bambi.order.application.dto.query.OrderByUserIdQuery;
import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByUserIdQueryHandler implements QueryHandler<Flux<OrderResponse>, OrderByUserIdQuery> {

    private final OrderRepository orderRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    @Override
    public Flux<OrderResponse> handle(OrderByUserIdQuery query) {
        UserId userId = query.userId();

        return orderRepository
                .findByUserId(userId)
                .switchIfEmpty(Flux.error(new OrderNotFoundException("Orders with user id: " + userId.getValue() +
                        " could not be found"))
                )
                .map(orderApplicationMapper::toOrderResponse);
    }
}
