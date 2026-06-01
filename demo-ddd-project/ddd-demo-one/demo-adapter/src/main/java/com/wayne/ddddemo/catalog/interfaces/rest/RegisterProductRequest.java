package com.wayne.ddddemo.catalog.interfaces.rest;

import com.wayne.ddddemo.catalog.application.command.RegisterProductCommand;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterProductRequest(
        @NotBlank(message = "Product id is required")
        String productId,
        @NotBlank(message = "Product name is required")
        String name,
        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.01", message = "Product price must be greater than zero")
        BigDecimal price
) {

    public RegisterProductCommand toCommand() {
        return new RegisterProductCommand(productId, name, price);
    }
}
