package com.wayne.ddddemotwo.customer.application.command;

import com.wayne.ddddemotwo.customer.domain.model.CustomerLevel;

import java.math.BigDecimal;

public record RegisterCustomerCommand(String customerId, String name, CustomerLevel level, BigDecimal creditLimit) {
}
