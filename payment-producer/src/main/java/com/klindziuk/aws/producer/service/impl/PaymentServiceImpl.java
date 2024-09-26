/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.service.impl;

import com.klindziuk.aws.producer.mapper.PaymentMapper;
import com.klindziuk.aws.producer.model.repository.PaymentEntity;
import com.klindziuk.aws.producer.model.request.PaymentRequest;
import com.klindziuk.aws.producer.model.request.PaymentResponse;
import com.klindziuk.aws.producer.repository.PaymentRepository;
import com.klindziuk.aws.producer.service.PaymentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final PaymentMapper paymentMapper;

  @Autowired
  public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
    this.paymentRepository = paymentRepository;
    this.paymentMapper = paymentMapper;
  }

  @Override
  public List<PaymentResponse> getPayments() {
    return paymentRepository.getPayments().stream()
        .map(paymentMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public PaymentResponse getPaymentById(String id) {
    return paymentMapper.toResponse(paymentRepository.getPaymentById(id));
  }

  @Override
  public PaymentResponse savePayment(PaymentRequest paymentRequest) {
    final PaymentEntity paymentEntity =
        paymentRepository.savePayment(paymentMapper.toPaymentEntity(paymentRequest));
    return paymentMapper.toResponse(paymentEntity);
  }

  @Override
  public PaymentResponse deletePayment(String id) {
    final PaymentEntity paymentEntity = paymentRepository.deletePayment(id);
    return paymentMapper.toResponse(paymentEntity);
  }
}
