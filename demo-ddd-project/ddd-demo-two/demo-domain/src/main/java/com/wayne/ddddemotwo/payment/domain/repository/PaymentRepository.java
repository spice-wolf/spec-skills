package com.wayne.ddddemotwo.payment.domain.repository;

import com.wayne.ddddemotwo.payment.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> findById(String id);

    Optional<Payment> findByOrderId(String orderId);

    List<Payment> findAll();

    Payment save(Payment payment);
}
