package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.infrastructure.persistence.order.entity.OrderStatusHistoryEntity;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderStatusHistoryR2dbcDatabaseClient {

    private final DatabaseClient databaseClient;
    private final OrderPersistenceMapper orderPersistenceMapper;

    public Mono<OrderStatusHistoryEntity> insert(OrderStatusHistoryEntity entity) {
        return databaseClient.sql("""
                        INSERT INTO order_status_history
                        VALUES (:id, :orderId, :orderStatus::type_order_status, :reason, :createdAt)
                        RETURNING *
                        """)
                .bind("id", entity.getId())
                .bind("orderId", entity.getOrderId())
                .bind("orderStatus", entity.getOrderStatus().name())
                .bind("reason", entity.getReason())
                .bind("createdAt", entity.getCreatedAt())
                .map(orderPersistenceMapper::rowToOrderStatusHistory)
                .one();
    }
}
