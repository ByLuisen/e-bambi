package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderByIdAndUserIdQuery;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByIdAndUserIdQueryHandler implements QueryHandler<Mono<OrderWithDetailReadResponse>, OrderByIdAndUserIdQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderWithDetailReadResponse> handle(OrderByIdAndUserIdQuery query) {
        UserId userId = query.getUserId();

        return orderQueryRepository
                .findByUserIdAndOrderId(userId, query.getOrderId())
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Order with user id: " + userId.getValue() +
                                " could not be found"))
                );
    }
}
