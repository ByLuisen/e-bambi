package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderByIdQuery;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByIdQueryHandler implements QueryHandler<Mono<OrderWithDetailReadResponse>, OrderByIdQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderWithDetailReadResponse> handle(OrderByIdQuery query) {
        UUID orderId = query.getOrderId().getValue();

        return orderQueryRepository
                .findByOrderId(query.getOrderId())
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Order with id: " + orderId + " could not be found"))
                );
    }
}
