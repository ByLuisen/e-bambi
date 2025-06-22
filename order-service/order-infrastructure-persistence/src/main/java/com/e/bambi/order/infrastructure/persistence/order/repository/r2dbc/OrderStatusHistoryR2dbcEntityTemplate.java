package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.infrastructure.persistence.order.entity.OrderStatusHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderStatusHistoryR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<OrderStatusHistoryEntity> insert(OrderStatusHistoryEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
