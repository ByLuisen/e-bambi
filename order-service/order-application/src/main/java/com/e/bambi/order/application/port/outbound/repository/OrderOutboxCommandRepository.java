package com.e.bambi.order.application.port.outbound.repository;

import com.e.bambi.order.application.outbox.model.OrderOutboxCommandMessage;

public interface OrderOutboxCommandRepository {
    OrderOutboxCommandMessage save(OrderOutboxCommandMessage orderOutboxCommandMessage);
}
