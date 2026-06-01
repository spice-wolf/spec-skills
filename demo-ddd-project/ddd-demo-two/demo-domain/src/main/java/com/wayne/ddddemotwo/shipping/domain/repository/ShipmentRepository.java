package com.wayne.ddddemotwo.shipping.domain.repository;

import com.wayne.ddddemotwo.shipping.domain.model.Shipment;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository {

    Optional<Shipment> findById(String id);

    Optional<Shipment> findByOrderId(String orderId);

    List<Shipment> findAll();

    Shipment save(Shipment shipment);
}
