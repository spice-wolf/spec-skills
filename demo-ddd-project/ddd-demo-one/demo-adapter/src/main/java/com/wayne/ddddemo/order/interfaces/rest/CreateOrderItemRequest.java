package com.wayne.ddddemo.order.interfaces.rest;

import com.wayne.ddddemo.order.application.command.CreateOrderItemCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemRequest(
        @NotBlank(message = "Product id is required")
        String productId,
        @Positive(message = "Quantity must be greater than zero")
        int quantity
) {

    public CreateOrderItemCommand toCommand() {
        return new CreateOrderItemCommand(productId, quantity);
    }
}
