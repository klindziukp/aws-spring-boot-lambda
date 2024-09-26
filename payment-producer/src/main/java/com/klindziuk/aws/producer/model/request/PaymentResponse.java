/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.model.request;

import java.time.Instant;
import lombok.Data;

@Data
public class PaymentResponse {

  private String id;
  private String status;
  private String customerId;
  private Double amount;
  private Instant paidAt;
}
