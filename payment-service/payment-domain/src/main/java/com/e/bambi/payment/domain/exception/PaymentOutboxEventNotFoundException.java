package com.e.bambi.payment.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class PaymentOutboxEventNotFoundException extends DomainException {
    public PaymentOutboxEventNotFoundException(String message) {
        super(message);
    }

    public PaymentOutboxEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
