package com.e.bambi.order.application.dto.query;

import com.e.bambi.order.application.dto.response.OrderStatusHistoryResponse;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public record OrderStatusHistoryByOrderIdQuery(OrderId orderId) implements Query<Flux<OrderStatusHistoryResponse>> {
}
