package com.wayne.ddddemo.order.application.event;

import com.wayne.ddddemo.order.application.dto.OrderDto;
import com.wayne.ddddemo.order.domain.model.OrderStatus;

public record OrderStatusChangedEvent(
        OrderDto order,
        OrderStatus previousStatus,
        OrderStatus currentStatus
) {
}
