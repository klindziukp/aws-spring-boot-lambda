/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer.controller;

import com.klindziuk.aws.consumer.client.PaymentProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

  @Autowired
  public PaymentController(PaymentProducerClient paymentProducerClient) {
    this.paymentProducerClient = paymentProducerClient;
  }

  private final PaymentProducerClient paymentProducerClient;

  @GetMapping(value = {"/", "/payments"})
  public String getPayments(Model model) {
    model.addAttribute("paymentsList", paymentProducerClient.getPayments());
    return "payments";
  }
}
