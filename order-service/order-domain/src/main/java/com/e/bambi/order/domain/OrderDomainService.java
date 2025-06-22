package com.e.bambi.order.domain;

import com.e.bambi.order.domain.event.OrderInventoryCancelReservationEvent;
import com.e.bambi.order.domain.event.OrderInventoryReserveEvent;
import com.e.bambi.order.domain.event.OrderPaymentValidateEvent;
import com.e.bambi.order.domain.order.entity.Order;

import java.util.List;

public interface OrderDomainService {

    public OrderInventoryReserveEvent validateAndInitiateOrder(String aggregatetype, Order order);

    public OrderInventoryCancelReservationEvent cancelOrderInventory(String aggregatetype, Order order,
                                                                     List<String> failureMessages);

    public OrderPaymentValidateEvent confirmOrderReservation(String aggregatetype, Order order);
}
