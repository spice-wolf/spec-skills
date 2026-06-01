package com.wayne.ddddemotwo.payment.application.command;

import java.math.BigDecimal;

public record RecordPaymentCommand(String paymentId, String orderId, BigDecimal amount, String method) {
}
