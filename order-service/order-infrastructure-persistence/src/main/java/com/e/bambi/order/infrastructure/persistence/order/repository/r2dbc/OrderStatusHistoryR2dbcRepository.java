package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderStatusHistoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderStatusHistoryR2dbcRepository extends R2dbcRepository<OrderStatusHistoryEntity, UUID> {

    @Query("SELECT order_status, reason, changed_at FROM order_status_history " +
            "WHERE order_id = :orderId")
    Flux<OrderStatusHistoryReadResponse> findByOrderId(UUID orderId);

    @Query("SELECT case when (count(osh) > 0) then true else false end " +
            "id FROM order_status_history osh " +
            "WHERE osh.order_id = :orderId AND osh.order_status = :orderStatus::type_order_status LIMIT 1")
    Mono<Boolean> existsByOrderIdAndOrderStatus(UUID orderId, OrderStatus orderStatus);

}
