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

    @Query("SELECT osh.orderStatus, osh.reason, osh.changedAt FROM OrderStatusHistoryEntity osh " +
            "WHERE osh.orderId = :orderId")
    Flux<OrderStatusHistoryReadResponse> findByOrderId(UUID orderId);

    Mono<Boolean> existsByOrderIdAndOrderStatus(UUID orderId, OrderStatus orderStatus);

}
