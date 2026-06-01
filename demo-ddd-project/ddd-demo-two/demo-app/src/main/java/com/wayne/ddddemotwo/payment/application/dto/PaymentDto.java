package com.wayne.ddddemotwo.payment.application.dto;

import com.wayne.ddddemotwo.payment.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto(
        String id,
        String orderId,
        BigDecimal amount,
        String method,
        LocalDateTime paidAt,
        PaymentStatus status
) {
}
