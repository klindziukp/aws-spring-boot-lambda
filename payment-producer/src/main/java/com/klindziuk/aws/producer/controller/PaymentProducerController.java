/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.controller;

import com.klindziuk.aws.producer.model.request.PaymentRequest;
import com.klindziuk.aws.producer.model.request.PaymentResponse;
import com.klindziuk.aws.producer.service.PaymentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentProducerController {

  private final PaymentService paymentService;

  @Autowired
  public PaymentProducerController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/api/v1/payments")
  public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
    log.info("Payment processing started {}", paymentRequest);
    return paymentService.savePayment(paymentRequest);
  }

  @GetMapping("/api/v1/payments/{id}")
  public PaymentResponse getPaymentById(@PathVariable String id) {
    log.info("Retrieving payment with id {}", id);
    return paymentService.getPaymentById(id);
  }

  @GetMapping("/api/v1/payments")
  public List<PaymentResponse> getPayments() {
    return paymentService.getPayments();
  }

  @DeleteMapping("/api/v1/payments/{id}")
  public PaymentResponse deletePayment(@PathVariable String id) {
    log.info("Delete payment with id {}", id);
    return paymentService.deletePayment(id);
  }
}
