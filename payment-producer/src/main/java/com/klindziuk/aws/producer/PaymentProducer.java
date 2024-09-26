/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentProducer {

  public static void main(String[] args) {
    SpringApplication.run(PaymentProducer.class, args);
  }
}
