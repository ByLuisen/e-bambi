package com.e.bambi.order.application.handler.query;

import com.e.bambi.order.application.dto.query.OrderStatusHistoryByOrderIdQuery;
import com.e.bambi.order.application.dto.response.OrderStatusHistoryResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderStatusHistoryRepository;
import com.e.bambi.order.domain.exception.OrderStatusHistoryNotFoundException;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class OrderStatusHistoryByOrderIdQueryHandler implements
        QueryHandler<Flux<OrderStatusHistoryResponse>, OrderStatusHistoryByOrderIdQuery> {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    @Override
    public Flux<OrderStatusHistoryResponse> handle(OrderStatusHistoryByOrderIdQuery query) {
        OrderId orderId = query.orderId();

        return orderStatusHistoryRepository
                .findByOrderId(orderId)
                .switchIfEmpty(
                        Flux.error(new OrderStatusHistoryNotFoundException("Status history with order id: " +
                                orderId.getValue() + " could not be found"))
                )
                .map(orderApplicationMapper::toOrderStatusHistoryResponse);
    }
}
