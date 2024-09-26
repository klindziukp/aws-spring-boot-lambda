/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.producer.config;

import com.klindziuk.aws.producer.model.repository.PaymentEntity;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.annotations.NotNull;

@Configuration
public class CustomDynamoDbTableNameResolver implements DynamoDbTableNameResolver {

  @NotNull @Override
  public <T> String resolve(@NotNull Class<T> clazz) {
    return classTableNameMap().get(clazz);
  }

  private Map<Class<?>, String> classTableNameMap() {
    return Map.of(PaymentEntity.class, "payments");
  }
}
