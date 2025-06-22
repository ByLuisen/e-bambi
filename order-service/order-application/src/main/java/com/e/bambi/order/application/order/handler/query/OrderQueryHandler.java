package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueryHandler implements QueryHandler<Mono<PaginatedResultResponse<OrderSummaryReadResponse>>, OrderQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> handle(OrderQuery query) {
        return orderQueryRepository.findAll(query)
                .switchIfEmpty(
                        Mono.error(new OrderNotFoundException("Orders could not be found"))
                );
    }
}
