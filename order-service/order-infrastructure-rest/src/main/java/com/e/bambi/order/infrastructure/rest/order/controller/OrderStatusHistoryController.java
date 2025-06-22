package com.e.bambi.order.infrastructure.rest.order.controller;

import com.e.bambi.order.application.order.dto.query.OrderStatusHistoryByUserIdAndOrderIdQuery;
import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderStatusHistoryController {

    private final QueryBus queryBus;

    @GetMapping("/me/orders/{orderId}/status-history")
    public Flux<OrderStatusHistoryReadResponse> getStatusHistoryForOrder(@PathVariable @UUID String orderId) {
        return queryBus.dispatch(new OrderStatusHistoryByUserIdAndOrderIdQuery(
                new OrderId(java.util.UUID.fromString(orderId)))
        );
    }
}
