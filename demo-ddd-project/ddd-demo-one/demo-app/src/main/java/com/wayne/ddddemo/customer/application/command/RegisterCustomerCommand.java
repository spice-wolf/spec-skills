package com.wayne.ddddemo.customer.application.command;

import com.wayne.ddddemo.customer.domain.model.CustomerLevel;

import java.math.BigDecimal;

public record RegisterCustomerCommand(String customerId, String name, CustomerLevel level, BigDecimal creditLimit) {
}
