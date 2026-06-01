package com.wayne.ddddemo.order.domain.repository;

import com.wayne.ddddemo.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(String id);

    List<Order> findAll();

    Order save(Order order);
}
