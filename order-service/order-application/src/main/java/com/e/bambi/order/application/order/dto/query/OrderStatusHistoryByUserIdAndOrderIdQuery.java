package com.e.bambi.order.application.order.dto.query;

import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Getter
@RequiredArgsConstructor
public class OrderStatusHistoryByUserIdAndOrderIdQuery extends Query<Flux<OrderStatusHistoryReadResponse>> {
    private final OrderId orderId;
}
