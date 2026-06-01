package com.wayne.ddddemotwo.shipping.application.dto;

import com.wayne.ddddemotwo.shipping.domain.model.ShipmentStatus;

import java.time.LocalDateTime;

public record ShipmentDto(
        String id,
        String orderId,
        String address,
        String carrier,
        String trackingNumber,
        LocalDateTime createdAt,
        ShipmentStatus status
) {
}
