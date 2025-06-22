package com.e.bambi.order.application.order.dto.query;

import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class OrderByIdQuery extends Query<Mono<OrderWithDetailReadResponse>> {
    private final OrderId orderId;
}
