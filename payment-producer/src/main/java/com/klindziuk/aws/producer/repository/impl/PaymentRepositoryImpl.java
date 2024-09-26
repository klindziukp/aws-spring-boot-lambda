/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.repository.impl;

import com.klindziuk.aws.producer.model.repository.PaymentEntity;
import com.klindziuk.aws.producer.repository.PaymentRepository;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Service
public class PaymentRepositoryImpl implements PaymentRepository {

  private final DynamoDbTemplate dynamoDbTemplate;

  @Autowired
  public PaymentRepositoryImpl(DynamoDbTemplate dynamoDbTemplate) {
    this.dynamoDbTemplate = dynamoDbTemplate;
  }

  @Override
  public List<PaymentEntity> getPayments() {
    PageIterable<PaymentEntity> news = dynamoDbTemplate.scanAll(PaymentEntity.class);
    return StreamSupport.stream(news.spliterator(), false)
        .flatMap(page -> page.items().stream())
        .sorted((n1, n2) -> n2.getPaidAt().compareTo(n1.getPaidAt()))
        .toList();
  }

  @Override
  public PaymentEntity getPaymentById(String id) {
    final Key key = Key.builder().partitionValue(id).build();
    final PaymentEntity paymentEntity = dynamoDbTemplate.load(key, PaymentEntity.class);
    if (Objects.nonNull(paymentEntity)) {
      return paymentEntity;
    }
    throw new RuntimeException(String.format("Payment [%s] not found", id));
  }

  @Override
  public PaymentEntity savePayment(PaymentEntity paymentEntity) {
    return dynamoDbTemplate.save(paymentEntity);
  }

  @Override
  public PaymentEntity deletePayment(String id) {
    final PaymentEntity paymentEntity = getPaymentById(id);
    dynamoDbTemplate.delete(paymentEntity);
    return paymentEntity;
  }
}
