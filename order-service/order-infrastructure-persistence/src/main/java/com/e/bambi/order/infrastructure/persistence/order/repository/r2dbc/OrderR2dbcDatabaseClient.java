package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderR2dbcDatabaseClient {

    private final DatabaseClient databaseClient;
    private final OrderPersistenceMapper orderPersistenceMapper;

    public Mono<OrderEntity> insert(OrderEntity entity) {
        return databaseClient.sql("""
                        INSERT INTO orders
                        VALUES (:id, :userId, :orderStatus::type_order_status, :paymentMethodId, :paymentMethod, :country, 
                        :address, :city, :province, :postalCode, :phoneNumber, :totalPrice, :failureMessages, :createdAt)
                        RETURNING *
                        """)
                .bind("id", entity.getId())
                .bind("userId", entity.getUserId())
                .bind("orderStatus", entity.getOrderStatus().name())
                .bind("paymentMethodId", entity.getPaymentMethodId())
                .bind("paymentMethod", entity.getPaymentMethod())
                .bind("country", entity.getCountry())
                .bind("address", entity.getAddress())
                .bind("city", entity.getCity())
                .bind("province", entity.getProvince())
                .bind("postalCode", entity.getPostalCode())
                .bind("phoneNumber", entity.getPhoneNumber())
                .bind("totalPrice", entity.getTotalPrice())
                .bind("failureMessages", entity.getFailureMessages())
                .bind("createdAt", entity.getCreatedAt())
                .map(orderPersistenceMapper::rowToOrderEntity)
                .one();
    }

    public Mono<OrderEntity> update(OrderEntity entity) {
        return databaseClient.sql("""
                        UPDATE orders
                        SET order_status = :orderStatus::type_order_status, failure_messages = :failureMessages
                        WHERE id = :id
                        RETURNING *
                        """)
                .bind("id", entity.getId())
                .bind("orderStatus", entity.getOrderStatus().name())
                .bind("failureMessages", entity.getFailureMessages())
                .map(orderPersistenceMapper::rowToOrderEntity)
                .one();
    }
}
