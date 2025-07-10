package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderBadRequestException;
import com.e.bambi.order.domain.exception.OrderDomainException;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderQueryHandler implements QueryHandler<Mono<PaginatedResultResponse<OrderSummaryReadResponse>>, OrderQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> handle(OrderQuery query) {
        return ensureFiltersAreValid(query)
                .then(orderQueryRepository.findAll(query)
                        .switchIfEmpty(
                                Mono.error(new OrderNotFoundException("Orders could not be found"))
                        )
                );
    }

    private Mono<Void> ensureFiltersAreValid(OrderQuery query) {
        List<String> errors = new ArrayList<>();
        if (query.getTotalPrice() != null && !query.getTotalPrice().getFirst().max(query.getTotalPrice().getLast())
                .equals(query.getTotalPrice().getLast())) {
            errors.add("First price should be less than the second");
        }
        if (query.getCreatedAt() != null && !query.getCreatedAt().getFirst().isBefore(query.getCreatedAt().getLast())) {
            errors.add("First date should be before than the second");
        }
        if (!errors.isEmpty()) {
            return Mono.error(new OrderBadRequestException("Invalid filters", errors));
        }
        return Mono.empty();
    }
}
