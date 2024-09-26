/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer.client;

import com.klindziuk.aws.model.PaymentResponse;
import java.util.List;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/")
public interface PaymentProducerClient {

  @GetExchange("api/v1/payments")
  List<PaymentResponse> getPayments();
}
