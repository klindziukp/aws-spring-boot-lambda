/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.handler.service;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.klindziuk.aws.model.Payment;
import com.klindziuk.aws.model.PaymentEvent;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DynamodbEventHandler implements Consumer<DynamodbEvent> {

  private final PaymentEventPublisher paymentEventPublisher;

  @Override
  public void accept(DynamodbEvent dynamodbEvent) {
    dynamodbEvent.getRecords().stream()
        .map(this::toNewsEvent)
        .forEach(paymentEventPublisher::publish);
  }

  private PaymentEvent toNewsEvent(DynamodbEvent.DynamodbStreamRecord record) {
    Map<String, AttributeValue> image = record.getDynamodb().getNewImage();
    if (Objects.isNull(image)) {
      image = record.getDynamodb().getOldImage();
    }
    final Payment payment =
        new Payment(
            image.get("id").getS(),
            image.get("customerId").getS(),
            Double.parseDouble(image.get("amount").getN()),
            image.get("paidAt").getS());
    return new PaymentEvent(record.getEventName(), payment);
  }
}
