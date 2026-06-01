package com.wayne.ddddemo.order.application.command;

public record CreateOrderItemCommand(String productId, int quantity) {
}
