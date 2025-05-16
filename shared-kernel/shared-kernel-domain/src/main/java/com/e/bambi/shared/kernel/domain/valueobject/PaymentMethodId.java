package com.e.bambi.shared.kernel.domain.valueobject;

import java.util.UUID;

public class PaymentMethodId extends BaseId<UUID> {
    public PaymentMethodId(UUID value) {
        super(value);
    }
}
