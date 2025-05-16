package com.e.bambi.payment.infrastructure.messaging.dto.listener;

public record ValidatePaymentMessageDTO(String orderId, String paymentMethodId) {
}
