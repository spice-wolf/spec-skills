package com.wayne.ddddemotwo.shipping.infrastructure.repository;

import com.wayne.ddddemotwo.shipping.domain.model.Shipment;
import com.wayne.ddddemotwo.shipping.domain.repository.ShipmentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryShipmentRepository implements ShipmentRepository {

    private final ConcurrentMap<String, Shipment> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Shipment> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Shipment> findByOrderId(String orderId) {
        return storage.values().stream().filter(shipment -> shipment.getOrderId().equals(orderId)).findFirst();
    }

    @Override
    public List<Shipment> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Shipment save(Shipment shipment) {
        storage.put(shipment.getId(), shipment);
        return shipment;
    }
}
