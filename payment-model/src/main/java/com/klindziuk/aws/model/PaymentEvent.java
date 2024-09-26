/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.aws.model;

public record PaymentEvent(String action, Payment payment) {}
