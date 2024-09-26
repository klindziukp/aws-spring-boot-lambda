/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.model.repository;

import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Setter
@NoArgsConstructor
@DynamoDbBean
public class PaymentEntity {
  private String id;
  private String customerId;
  private String status;
  private Double amount;
  private Instant paidAt;

  @DynamoDbPartitionKey
  public String getId() {
    return id;
  }
}
