package com.wayne.ddddemotwo.shipping.application.command;

public record CreateShipmentCommand(
        String shipmentId,
        String orderId,
        String address,
        String carrier,
        String trackingNumber
) {
}
