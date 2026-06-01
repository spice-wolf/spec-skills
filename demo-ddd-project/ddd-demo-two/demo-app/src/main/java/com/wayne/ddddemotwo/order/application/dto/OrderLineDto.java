package com.wayne.ddddemotwo.order.application.dto;

import java.math.BigDecimal;

public record OrderLineDto(String productId, String productName, BigDecimal unitPrice, int quantity, BigDecimal subtotal) {
}
