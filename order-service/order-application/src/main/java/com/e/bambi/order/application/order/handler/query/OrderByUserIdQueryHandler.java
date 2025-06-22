package com.e.bambi.order.application.order.handler.query;

import com.e.bambi.order.application.order.dto.query.OrderByUserIdQuery;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderQueryRepository;
import com.e.bambi.order.domain.exception.OrderDomainException;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Year;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderByUserIdQueryHandler implements
        QueryHandler<Mono<PaginatedResultResponse<OrderSummaryReadResponse>>, OrderByUserIdQuery> {

    private final OrderQueryRepository orderQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> handle(OrderByUserIdQuery query) {
        UserId userId = query.getUserId();

        return ensureDateIsValida(query.getDate())
                .flatMap(validDate -> {
                    if (!validDate) {
                        return Mono.error(
                                new OrderDomainException("Year must be less than or equal to the current year"));
                    }

                    return orderQueryRepository
                            .findByUserId(userId, query.getPage(), query.getDate())
                            .switchIfEmpty(Mono.error(new OrderNotFoundException("Orders with user id: "
                                    + userId.getValue() + " could not be found"))
                            );
                });
    }

    private Mono<Boolean> ensureDateIsValida(Integer date) {
        if (date == 3 || date == 30) {
            return Mono.just(true);
        }

        return Mono.just(date <= Year.now().getValue());
    }
}
