/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentConsumer {

  public static void main(String[] args) {
    SpringApplication.run(PaymentConsumer.class, args);
  }
}
