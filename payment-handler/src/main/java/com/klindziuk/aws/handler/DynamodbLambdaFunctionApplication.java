/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DynamodbLambdaFunctionApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext run =
        SpringApplication.run(DynamodbLambdaFunctionApplication.class, args);
    String[] allBeanNames = run.getBeanDefinitionNames();
    for (String beanName : allBeanNames) {
      System.out.println(beanName);
    }
    //        PaymentEventPublisher paymentEventPublisher =
    // run.getBean(PaymentEventPublisher.class);
    //        final PaymentEvent payment = new PaymentEvent("IN_PROGRESS", new Payment("ff", "11",
    // 53.2, "23"));
    //        paymentEventPublisher.publish(payment);
  }
}
