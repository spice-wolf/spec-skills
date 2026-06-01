package com.wayne.ddddemo.order.application.dto;

import com.wayne.ddddemo.order.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        String id,
        String customerId,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderLineDto> lines,
        String traceId
) {
}
