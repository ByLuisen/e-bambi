package com.e.bambi.order.application.dto.query;

import com.e.bambi.order.application.dto.response.CreateOrderResponse;
import com.e.bambi.order.domain.valueobject.TrackingId;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Mono;

public record TrackOrderQuery(TrackingId orderTrackingId) implements Query<Mono<CreateOrderResponse>> {
}
