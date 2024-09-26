/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AwsConfig {

  @Value("${com.klindziuk.aws.sqs.payment.queue.name:payments-queue}")
  public String paymentQueue;
}
