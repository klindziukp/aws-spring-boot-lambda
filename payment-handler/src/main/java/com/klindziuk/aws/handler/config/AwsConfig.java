/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.handler.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AwsConfig {

  @Value("${com.klindziuk.aws.sns.payment.topic.name:payments-topic}")
  public String paymentTopic;
}
