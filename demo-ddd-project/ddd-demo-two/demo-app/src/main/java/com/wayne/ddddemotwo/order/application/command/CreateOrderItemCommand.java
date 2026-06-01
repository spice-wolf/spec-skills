package com.wayne.ddddemotwo.order.application.command;

public record CreateOrderItemCommand(String productId, int quantity) {
}
