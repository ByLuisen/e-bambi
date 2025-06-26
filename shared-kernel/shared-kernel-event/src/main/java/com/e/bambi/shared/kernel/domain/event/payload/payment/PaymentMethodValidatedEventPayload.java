package com.e.bambi.shared.kernel.domain.event.payload.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentMethodValidatedEventPayload extends PaymentMethodEventPayload {

    public PaymentMethodValidatedEventPayload(UUID orderId) {
        super(orderId);
    }
}
