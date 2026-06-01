package com.wayne.ddddemotwo.order.application.command;

import java.util.List;

public record CreateOrderCommand(String orderId, String customerId, List<CreateOrderItemCommand> items) {
}
