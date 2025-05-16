package com.e.bambi.order.application.dto.query;

import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public record OrderByUserIdQuery() implements Query<Flux<OrderResponse>> {
}
