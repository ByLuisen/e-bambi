package com.e.bambi.order.application.handler.query;

import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.order.application.dto.query.OrderQuery;
import com.e.bambi.order.application.dto.response.PaginatedResultResponse;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueryHandler implements QueryHandler<Mono<PaginatedResultResponse<OrderResponse>>, OrderQuery> {

    private final OrderRepository orderRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    @Override
    public Mono<PaginatedResultResponse<OrderResponse>> handle(OrderQuery query) {
        return orderRepository.findAllByFilter(query)
                .switchIfEmpty(Flux.error(new OrderNotFoundException("Orders could not be found")))
                .collectList()
                .map(orderApplicationMapper::toPaginatedResultResponse);
    }
}
