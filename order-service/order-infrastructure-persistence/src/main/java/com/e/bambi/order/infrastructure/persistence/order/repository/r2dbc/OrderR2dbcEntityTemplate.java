package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<OrderEntity> insert(OrderEntity orderEntity) {
        return r2dbcEntityTemplate.insert(orderEntity);
    }
}
