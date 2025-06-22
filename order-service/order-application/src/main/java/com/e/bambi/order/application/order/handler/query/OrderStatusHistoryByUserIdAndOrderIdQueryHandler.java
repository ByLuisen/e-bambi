package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderStatusHistoryByUserIdAndOrderIdQuery;
import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderStatusHistoryQueryRepository;
import com.e.bambi.order.domain.exception.OrderStatusHistoryNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class OrderStatusHistoryByUserIdAndOrderIdQueryHandler implements
        QueryHandler<Flux<OrderStatusHistoryReadResponse>, OrderStatusHistoryByUserIdAndOrderIdQuery> {

    private final OrderStatusHistoryQueryRepository orderStatusHistoryQueryRepository;

    @Override
    @Transactional
    public Flux<OrderStatusHistoryReadResponse> handle(OrderStatusHistoryByUserIdAndOrderIdQuery query) {
        OrderId orderId = query.getOrderId();

        return orderStatusHistoryQueryRepository
                .findByOrderId(orderId)
                .switchIfEmpty(
                        Flux.error(new OrderStatusHistoryNotFoundException("Status history with order id: " +
                                orderId.getValue() + " could not be found"))
                );
    }
}
