package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.model.OrderStatusHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderStatusHistoryCrudRepository extends ReactiveCrudRepository<OrderStatusHistory, UUID> {
}
