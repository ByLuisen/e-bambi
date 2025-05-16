package com.e.bambi.order.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {
  public OrderNotFoundException(String message) {
    super(message);
  }

  public OrderNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
