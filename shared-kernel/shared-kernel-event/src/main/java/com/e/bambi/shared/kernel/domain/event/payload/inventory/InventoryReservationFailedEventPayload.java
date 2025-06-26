package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class InventoryReservationFailedEventPayload extends InventoryEventPayload {
        @JsonProperty
        private List<String> failureMessages;

        public InventoryReservationFailedEventPayload(UUID orderId, List<String> failureMessages) {
                super(orderId);
                this.failureMessages = failureMessages;
        }
}
