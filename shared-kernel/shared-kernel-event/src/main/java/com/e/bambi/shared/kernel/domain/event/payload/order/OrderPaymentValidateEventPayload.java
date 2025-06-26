package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderPaymentValidateEventPayload extends OrderEventPayload {
    @JsonProperty
    private UUID paymentMethodId;

    public OrderPaymentValidateEventPayload(UUID orderId, UUID paymentMethodId) {
        super(orderId);
        this.paymentMethodId = paymentMethodId;
    }
}
