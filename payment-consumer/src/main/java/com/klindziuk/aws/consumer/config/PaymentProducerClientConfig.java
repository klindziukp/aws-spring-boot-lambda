/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.consumer.config;

import com.klindziuk.aws.consumer.client.PaymentProducerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PaymentProducerClientConfig {

  @Value("${com.klindziuk.api.producer.base-url}")
  private String paymentProducerBaseUrl;

  @Bean
  public PaymentProducerClient paymentProducerClient() {
    final RestClient restClient = RestClient.builder().baseUrl(paymentProducerBaseUrl).build();
    final RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(PaymentProducerClient.class);
  }
}
