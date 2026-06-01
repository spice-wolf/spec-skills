package com.wayne.ddddemotwo.payment.application;

import com.wayne.ddddemotwo.order.domain.model.Order;
import com.wayne.ddddemotwo.order.domain.model.OrderStatus;
import com.wayne.ddddemotwo.order.domain.repository.OrderRepository;
import com.wayne.ddddemotwo.payment.application.command.RecordPaymentCommand;
import com.wayne.ddddemotwo.payment.application.dto.PaymentDto;
import com.wayne.ddddemotwo.payment.domain.model.Payment;
import com.wayne.ddddemotwo.payment.domain.repository.PaymentRepository;
import com.wayne.ddddemotwo.shared.domain.DomainException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PaymentApplicationService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentApplicationService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentDto recordPayment(RecordPaymentCommand command) {
        paymentRepository.findById(command.paymentId()).ifPresent(existing -> {
            throw new DomainException("Payment already exists: " + existing.getId());
        });
        paymentRepository.findByOrderId(command.orderId()).ifPresent(existing -> {
            throw new DomainException("Payment already recorded for order: " + existing.getOrderId());
        });

        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new DomainException("Order not found: " + command.orderId()));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new DomainException("Only created orders can be paid");
        }
        if (order.totalAmount().compareTo(command.amount()) != 0) {
            throw new DomainException("Payment amount must equal the order total");
        }

        Payment payment = new Payment(command.paymentId(), command.orderId(), command.amount(), command.method());
        order.markPaid();
        orderRepository.save(order);
        return toDto(paymentRepository.save(payment));
    }

    public List<PaymentDto> listPayments() {
        return paymentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Payment::getPaidAt))
                .map(this::toDto)
                .toList();
    }

    private PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getPaidAt(),
                payment.getStatus()
        );
    }
}
