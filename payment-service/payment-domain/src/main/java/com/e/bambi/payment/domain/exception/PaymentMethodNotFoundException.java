package com.e.bambi.payment.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class PaymentMethodNotFoundException extends DomainException {
    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
