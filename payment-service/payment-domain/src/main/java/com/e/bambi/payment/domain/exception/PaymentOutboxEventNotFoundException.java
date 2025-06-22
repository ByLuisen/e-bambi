package com.e.bambi.payment.domain.exception;

public class PaymentOutboxEventNotFoundException extends PaymentDomainException {
    public PaymentOutboxEventNotFoundException(String message) {
        super(message);
    }

    public PaymentOutboxEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
