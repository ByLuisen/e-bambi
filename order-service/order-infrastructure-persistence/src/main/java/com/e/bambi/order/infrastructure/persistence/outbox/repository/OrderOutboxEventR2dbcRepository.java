package com.e.bambi.order.infrastructure.persistence.outbox.repository;

import com.e.bambi.order.infrastructure.persistence.outbox.entity.OrderOutboxEventEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderOutboxEventR2dbcRepository extends R2dbcRepository<OrderOutboxEventEntity, UUID> {

    @Modifying
    @Query("DELETE FROM order_outbox_events WHERE id = :orderOutboxEventId")
    Mono<Integer> deleteOrderOutboxEventById(UUID orderOutboxEventId);
}
