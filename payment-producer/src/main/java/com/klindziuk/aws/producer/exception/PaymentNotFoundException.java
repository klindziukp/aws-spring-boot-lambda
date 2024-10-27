/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.exception;

public class PaymentNotFoundException extends RuntimeException {

  public PaymentNotFoundException(String id) {
    super(String.format("Payment [%s] not found", id));
  }
}
