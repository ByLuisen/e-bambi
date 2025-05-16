package com.e.bambi.order.application.dto.query;

import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Mono;

public record OrderByUserIdAndOrderIdQuery(OrderId orderId) implements Query<Mono<OrderResponse>> {
}
