package com.wayne.ddddemotwo.customer.interfaces.rest;

import com.wayne.ddddemotwo.customer.application.command.RegisterCustomerCommand;
import com.wayne.ddddemotwo.customer.domain.model.CustomerLevel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterCustomerRequest(
        @NotBlank(message = "Customer id is required")
        String customerId,
        @NotBlank(message = "Customer name is required")
        String name,
        @NotNull(message = "Customer level is required")
        CustomerLevel level,
        @NotNull(message = "Credit limit is required")
        @DecimalMin(value = "0.00", message = "Credit limit cannot be negative")
        BigDecimal creditLimit
) {

    public RegisterCustomerCommand toCommand() {
        return new RegisterCustomerCommand(customerId, name, level, creditLimit);
    }
}
