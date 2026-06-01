package com.wayne.ddddemotwo.payment.infrastructure.repository;

import com.wayne.ddddemotwo.payment.domain.model.Payment;
import com.wayne.ddddemotwo.payment.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

    private final ConcurrentMap<String, Payment> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return storage.values().stream().filter(payment -> payment.getOrderId().equals(orderId)).findFirst();
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Payment save(Payment payment) {
        storage.put(payment.getId(), payment);
        return payment;
    }
}
