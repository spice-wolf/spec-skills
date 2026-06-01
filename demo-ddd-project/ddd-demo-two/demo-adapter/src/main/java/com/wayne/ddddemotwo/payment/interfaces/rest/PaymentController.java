package com.wayne.ddddemotwo.payment.interfaces.rest;

import com.wayne.ddddemotwo.payment.application.PaymentApplicationService;
import com.wayne.ddddemotwo.payment.application.dto.PaymentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    public PaymentController(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @PostMapping
    public PaymentDto recordPayment(@Valid @RequestBody RecordPaymentRequest request) {
        return paymentApplicationService.recordPayment(request.toCommand());
    }

    @GetMapping
    public List<PaymentDto> listPayments() {
        return paymentApplicationService.listPayments();
    }
}
