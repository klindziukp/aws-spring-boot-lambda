/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamodbLambdaFunctionApplication {

  public static void main(String[] args) {
    SpringApplication.run(DynamodbLambdaFunctionApplication.class, args);
  }
}
