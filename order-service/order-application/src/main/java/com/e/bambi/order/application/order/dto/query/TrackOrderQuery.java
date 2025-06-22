package com.e.bambi.order.application.order.dto.query;

import com.e.bambi.order.application.order.dto.response.CreateOrderResponse;
import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.domain.order.valueobject.TrackingId;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class TrackOrderQuery extends Query<Mono<TrackOrderReadResponse>> {
    private final UserId userId;
    private final OrderId orderId;
}
