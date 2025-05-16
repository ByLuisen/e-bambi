package com.e.bambi.order.application.handler.query;

import com.e.bambi.order.application.dto.query.OrderByIdQuery;
import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByIdQueryHandler implements QueryHandler<Mono<OrderResponse>, OrderByIdQuery> {

    private final OrderRepository orderRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    @Override
    public Mono<OrderResponse> handle(OrderByIdQuery query) {
        UUID orderId = query.orderId().getValue();

        return orderRepository
                .findByOrderId(query.orderId())
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Order with id: " + orderId + " could not be found"))
                )
                .map(orderApplicationMapper::toOrderResponse);
    }
}
