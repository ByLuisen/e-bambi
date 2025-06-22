package com.e.bambi.payment.domain.exception;

public class PaymentMethodNotFoundException extends PaymentDomainException {
    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
