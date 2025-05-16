package com.e.bambi.payment.application.dto.response;

import java.util.UUID;

public record PaymentMethodResponse(UUID id, String name, String description) {
}
