package com.e.bambi.order.domain;

import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.order.domain.event.OrderCreatedEvent;
import com.e.bambi.shared.kernel.domain.event.publisher.DomainEventPublisher;

public class OrderDomainServiceImpl implements OrderDomainService {

    public OrderCreatedEvent validateAndInitiateOrder(Order order,
                                                      DomainEventPublisher<OrderCreatedEvent>
                                                              orderCreatedEventDomainEventPublisher) {
        return null;
    }
}
