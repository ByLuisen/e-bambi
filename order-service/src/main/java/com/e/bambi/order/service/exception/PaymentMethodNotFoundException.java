package com.e.bambi.order.service.exception;

public class PaymentMethodNotFoundException extends RuntimeException {
  public PaymentMethodNotFoundException(String message) {
    super(message);
  }
}
