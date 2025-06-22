package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.TrackOrderQuery;
import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TrackOrderQueryHandler implements QueryHandler<Mono<TrackOrderReadResponse>, TrackOrderQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    public Mono<TrackOrderReadResponse> handle(TrackOrderQuery query) {
        return orderQueryRepository.trackOrder(query.getUserId(), query.getOrderId())
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Order with id: " + query.getOrderId().getValue() +
                                " could not be found"))
                );
    }
}
