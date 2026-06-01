package com.wayne.ddddemotwo.inventory.application.dto;

public record StockItemDto(String productId, int availableQuantity, String traceId) {
}
