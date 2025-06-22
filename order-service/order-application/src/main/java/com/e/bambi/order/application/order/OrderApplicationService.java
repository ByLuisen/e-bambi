package com.e.bambi.order.application.order;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Mono;

public interface OrderApplicationService {

    Mono<Order> findOrder(OrderId orderId);

    SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus);
}
