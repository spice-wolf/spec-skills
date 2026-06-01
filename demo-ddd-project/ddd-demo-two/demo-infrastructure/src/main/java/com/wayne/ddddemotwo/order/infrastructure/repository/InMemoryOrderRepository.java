package com.wayne.ddddemotwo.order.infrastructure.repository;

import com.wayne.ddddemotwo.order.domain.model.Order;
import com.wayne.ddddemotwo.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final ConcurrentMap<String, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Order save(Order order) {
        storage.put(order.getId(), order);
        return order;
    }
}
