package com.e.bambi.order.infrastructure.messaging.mapper;

import com.e.bambi.order.application.order.dto.command.message.inventory.ReservationCancelledInventoryCommand;
import com.e.bambi.order.application.order.dto.command.message.inventory.ReservationFailedInventoryCommand;
import com.e.bambi.order.application.order.dto.command.message.inventory.ReservedInventoryCommand;
import com.e.bambi.order.application.order.dto.command.message.payment.ValidatedPaymentCommand;
import com.e.bambi.order.application.order.dto.command.message.payment.ValidationFailedPaymentCommand;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservationCancelledEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservationFailedEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservedEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidatedEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidationFailedEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import org.springframework.stereotype.Component;

@Component
public class OrderMessagingMapper {

    public ValidatedPaymentCommand toValidatedPaymentCommand(PaymentMethodValidatedEventPayload payload) {
        return new ValidatedPaymentCommand(
                new OrderId(payload.getOrderId())
        );
    }

    public ValidationFailedPaymentCommand toValidationFailedPaymentCommand(PaymentMethodValidationFailedEventPayload
                                                                                   payload, String sagaId) {
        return new ValidationFailedPaymentCommand(
                sagaId,
                new OrderId(payload.getOrderId()),
                payload.getFailureMessages()
        );
    }

    public ReservationCancelledInventoryCommand
    toReservationCancelledInventoryCommand(InventoryReservationCancelledEventPayload payload) {
        return new ReservationCancelledInventoryCommand(
                new OrderId(payload.getOrderId())
        );
    }

    public ReservationFailedInventoryCommand toReservationFailedInventoryCommand(InventoryReservationFailedEventPayload
                                                                                         payload) {
        return new ReservationFailedInventoryCommand(
                new OrderId(payload.getOrderId()),
                payload.getFailureMessages()
        );
    }

    public ReservedInventoryCommand toReservedInventoryCommand(String sagaId, InventoryReservedEventPayload payload) {
        return new ReservedInventoryCommand(
                sagaId,
                new OrderId(payload.getOrderId())
        );
    }
}
