package com.wayne.ddddemotwo.shipping.application;

import com.wayne.ddddemotwo.order.domain.model.Order;
import com.wayne.ddddemotwo.order.domain.model.OrderStatus;
import com.wayne.ddddemotwo.order.domain.repository.OrderRepository;
import com.wayne.ddddemotwo.shared.domain.DomainException;
import com.wayne.ddddemotwo.shipping.application.command.CreateShipmentCommand;
import com.wayne.ddddemotwo.shipping.application.dto.ShipmentDto;
import com.wayne.ddddemotwo.shipping.domain.model.Shipment;
import com.wayne.ddddemotwo.shipping.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ShippingApplicationService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShippingApplicationService(ShipmentRepository shipmentRepository, OrderRepository orderRepository) {
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
    }

    public ShipmentDto createShipment(CreateShipmentCommand command) {
        shipmentRepository.findById(command.shipmentId()).ifPresent(existing -> {
            throw new DomainException("Shipment already exists: " + existing.getId());
        });
        shipmentRepository.findByOrderId(command.orderId()).ifPresent(existing -> {
            throw new DomainException("Shipment already exists for order: " + existing.getOrderId());
        });

        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new DomainException("Order not found: " + command.orderId()));

        if (order.getStatus() != OrderStatus.PAID) {
            throw new DomainException("Only paid orders can be shipped");
        }

        Shipment shipment = new Shipment(
                command.shipmentId(),
                command.orderId(),
                command.address(),
                command.carrier(),
                command.trackingNumber()
        );
        shipment.dispatch();
        order.markShipped();
        orderRepository.save(order);
        return toDto(shipmentRepository.save(shipment));
    }

    public List<ShipmentDto> listShipments() {
        return shipmentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Shipment::getCreatedAt))
                .map(this::toDto)
                .toList();
    }

    private ShipmentDto toDto(Shipment shipment) {
        return new ShipmentDto(
                shipment.getId(),
                shipment.getOrderId(),
                shipment.getAddress(),
                shipment.getCarrier(),
                shipment.getTrackingNumber(),
                shipment.getCreatedAt(),
                shipment.getStatus()
        );
    }
}
