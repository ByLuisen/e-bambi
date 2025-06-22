package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderR2dbcRepository extends R2dbcRepository<OrderEntity, UUID> {

    @Query("SELECT id, order_status FROM orders WHERE id = :orderId")
    Mono<OrderEntity> findOrderById(UUID orderId);

    @Query("SELECT order_status, failure_messages FROM orders WHERE user_id = :userId AND order_id = :orderId")
    Mono<TrackOrderReadResponse> trackOrder(UUID userId, UUID orderId);

}
