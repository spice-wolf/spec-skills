package com.wayne.ddddemotwo.inventory.interfaces.rest;

import com.wayne.ddddemotwo.inventory.application.command.UpsertStockItemCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpsertStockItemRequest(
        @NotBlank(message = "Product id is required")
        String productId,
        @Min(value = 0, message = "Available quantity cannot be negative")
        int availableQuantity
) {

    public UpsertStockItemCommand toCommand() {
        return new UpsertStockItemCommand(productId, availableQuantity);
    }
}
