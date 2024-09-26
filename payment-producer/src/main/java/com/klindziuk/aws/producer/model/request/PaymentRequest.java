/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRequest {

  @NotBlank private String customerId;
  @NotBlank private Double amount;
}
