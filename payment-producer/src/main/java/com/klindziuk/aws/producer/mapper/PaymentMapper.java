/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.mapper;

import com.klindziuk.aws.producer.model.repository.PaymentEntity;
import com.klindziuk.aws.producer.model.request.PaymentRequest;
import com.klindziuk.aws.producer.model.request.PaymentResponse;
import java.time.Instant;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  PaymentResponse toResponse(PaymentEntity paymentEntity);

  @Mapping(target = "id", expression = "java(generateId())")
  @Mapping(target = "paidAt", expression = "java(generatePaidAt())")
  @Mapping(target = "status", expression = "java(generateStatus())")
  PaymentEntity toPaymentEntity(PaymentRequest paymentRequest);

  @Named("generateId")
  default String generateId() {
    return UUID.randomUUID().toString();
  }

  @Named("generatePaidAt")
  default Instant generatePaidAt() {
    return Instant.now();
  }

  @Named("generateStatus")
  default String generateStatus() {
    return "IN_PROGRESS";
  }
}
