package com.wayne.ddddemotwo.order.interfaces.rest;

import com.wayne.ddddemotwo.order.application.command.CreateOrderCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Order id is required")
        String orderId,
        @NotBlank(message = "Customer id is required")
        String customerId,
        @NotEmpty(message = "Order items are required")
        List<@Valid CreateOrderItemRequest> items
) {

    public CreateOrderCommand toCommand() {
        return new CreateOrderCommand(orderId, customerId, items.stream().map(CreateOrderItemRequest::toCommand).toList());
    }
}
