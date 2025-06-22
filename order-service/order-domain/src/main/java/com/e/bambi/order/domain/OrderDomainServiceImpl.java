package com.e.bambi.order.domain;

import com.e.bambi.order.domain.event.OrderInventoryCancelReservationEvent;
import com.e.bambi.order.domain.event.OrderInventoryReserveEvent;
import com.e.bambi.order.domain.event.OrderPaymentValidateEvent;
import com.e.bambi.order.domain.order.entity.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderInventoryReserveEvent validateAndInitiateOrder(String aggregatetype, Order order) {
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initialized", order.getId().getValue());
        return new OrderInventoryReserveEvent(aggregatetype, order);
    }

    @Override
    public OrderInventoryCancelReservationEvent cancelOrderInventory(String aggregatetype, Order order,
                                                                     List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order inventory is cancelling for order id: {}", order.getId().getValue());
        return new OrderInventoryCancelReservationEvent(aggregatetype, order);
    }

    @Override
    public OrderPaymentValidateEvent confirmOrderReservation(String aggregatetype, Order order) {
        order.productsReserved();
        return new OrderPaymentValidateEvent(aggregatetype, order);
    }
}
