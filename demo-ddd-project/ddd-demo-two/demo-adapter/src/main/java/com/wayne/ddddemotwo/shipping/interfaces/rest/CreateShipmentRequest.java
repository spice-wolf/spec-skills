package com.wayne.ddddemotwo.shipping.interfaces.rest;

import com.wayne.ddddemotwo.shipping.application.command.CreateShipmentCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateShipmentRequest(
        @NotBlank(message = "Shipment id is required")
        String shipmentId,
        @NotBlank(message = "Order id is required")
        String orderId,
        @NotBlank(message = "Address is required")
        String address,
        @NotBlank(message = "Carrier is required")
        String carrier,
        @NotBlank(message = "Tracking number is required")
        String trackingNumber
) {

    public CreateShipmentCommand toCommand() {
        return new CreateShipmentCommand(shipmentId, orderId, address, carrier, trackingNumber);
    }
}
