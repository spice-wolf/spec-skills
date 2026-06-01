package com.wayne.ddddemotwo.inventory.application.command;

public record UpsertStockItemCommand(String productId, int availableQuantity) {
}
