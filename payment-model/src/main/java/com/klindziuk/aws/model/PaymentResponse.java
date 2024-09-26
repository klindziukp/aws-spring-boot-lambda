/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.model;

public record PaymentResponse(String id, String customerId, Double amount, String publishedAt) {}
