package com.e.bambi.order.application.order;

import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderRepository orderRepository;

    @Override
    public Mono<Order> findOrder(OrderId orderId) {
        return orderRepository.findById(orderId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Order with id: {} could not be found!", orderId.getValue());

                    return Mono.error(new OrderNotFoundException("Order with id: " + orderId.getValue() + " could " +
                            "not be found!"));
                }));
    }

    @Override
    public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PRODUCTS_RESERVED -> SagaStatus.PROCESSING;
            case CREATED -> SagaStatus.COMPLETED;
            case CANCELLING -> SagaStatus.COMPENSATING;
            case CANCELLED -> SagaStatus.COMPENSATED;
            default -> SagaStatus.STARTED;
        };
    }
}
