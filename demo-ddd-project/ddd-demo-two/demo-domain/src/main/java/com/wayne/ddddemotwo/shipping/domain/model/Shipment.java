package com.wayne.ddddemotwo.shipping.domain.model;

import com.wayne.ddddemotwo.shared.domain.DomainException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Shipment {

    private final String id;
    private final String orderId;
    private final String address;
    private final String carrier;
    private final String trackingNumber;
    private final LocalDateTime createdAt;
    private ShipmentStatus status;

    public Shipment(String id, String orderId, String address, String carrier, String trackingNumber) {
        if (id == null || id.isBlank()) {
            throw new DomainException("Shipment id cannot be blank");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new DomainException("Order id cannot be blank");
        }
        if (address == null || address.isBlank()) {
            throw new DomainException("Address cannot be blank");
        }
        if (carrier == null || carrier.isBlank()) {
            throw new DomainException("Carrier cannot be blank");
        }
        if (trackingNumber == null || trackingNumber.isBlank()) {
            throw new DomainException("Tracking number cannot be blank");
        }
        this.id = id;
        this.orderId = orderId;
        this.address = address;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.createdAt = LocalDateTime.now();
        this.status = ShipmentStatus.CREATED;
    }

    public void dispatch() {
        if (status != ShipmentStatus.CREATED) {
            throw new DomainException("Only created shipments can be dispatched");
        }
        this.status = ShipmentStatus.DISPATCHED;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Shipment shipment)) {
            return false;
        }
        return Objects.equals(id, shipment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
