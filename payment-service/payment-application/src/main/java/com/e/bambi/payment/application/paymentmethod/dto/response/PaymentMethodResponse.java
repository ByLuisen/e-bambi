package com.e.bambi.payment.application.paymentmethod.dto.response;

import java.util.UUID;

public record PaymentMethodResponse(UUID id, String name, String description) {
}
