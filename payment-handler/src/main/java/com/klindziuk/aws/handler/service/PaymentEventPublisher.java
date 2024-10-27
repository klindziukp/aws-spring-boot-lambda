/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.handler.service;

import com.klindziuk.aws.handler.config.AwsConfig;
import com.klindziuk.aws.model.PaymentEvent;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PaymentEventPublisher {

  private final SnsTemplate snsTemplate;
  private final AwsConfig awsConfig;

  @Autowired
  public PaymentEventPublisher(SnsTemplate snsTemplate, AwsConfig awsConfig) {
    this.snsTemplate = snsTemplate;
    this.awsConfig = awsConfig;
  }

  public void publish(PaymentEvent paymentEvent) {
    log.info("Publishing payment event: {}", paymentEvent);
    snsTemplate.convertAndSend(awsConfig.getPaymentTopic(), paymentEvent);
  }
}
