/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.service;

import com.klindziuk.aws.producer.model.request.PaymentRequest;
import com.klindziuk.aws.producer.model.request.PaymentResponse;
import java.util.List;

public interface PaymentService {

  List<PaymentResponse> getPayments();

  PaymentResponse getPaymentById(String id);

  PaymentResponse savePayment(PaymentRequest paymentRequest);

  PaymentResponse deletePayment(String id);
}
