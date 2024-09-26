/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.model;

public record Payment(String id, String customerId, Double amount, String timestamp) {}
