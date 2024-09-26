/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.repository;

import com.klindziuk.aws.producer.model.repository.PaymentEntity;
import java.util.List;

public interface PaymentRepository {

  List<PaymentEntity> getPayments();

  PaymentEntity getPaymentById(String id);

  PaymentEntity savePayment(PaymentEntity paymentEntity);

  PaymentEntity deletePayment(String id);
}
