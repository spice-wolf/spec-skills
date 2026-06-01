package com.wayne.ddddemo.customer.application.dto;

import com.wayne.ddddemo.customer.domain.model.CustomerLevel;

import java.math.BigDecimal;

public record CustomerDto(String id, String name, CustomerLevel level, BigDecimal creditLimit, boolean active, String traceId) {
}
