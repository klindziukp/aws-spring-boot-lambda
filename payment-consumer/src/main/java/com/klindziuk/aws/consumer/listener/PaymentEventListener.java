/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer.listener;

import com.klindziuk.aws.model.PaymentEvent;
import io.awspring.cloud.sqs.annotation.SnsNotificationMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentEventListener {

  private final SimpMessagingTemplate simpMessagingTemplate;

  @SqsListener(value = "${com.klindziuk.aws.sqs.payment.queue.name}")
  public void paymentSqsListener(@SnsNotificationMessage PaymentEvent paymentEvent) {
    log.info("Received payment event: {}", paymentEvent);
    simpMessagingTemplate.convertAndSend("/topic/payments", paymentEvent);
  }
}
