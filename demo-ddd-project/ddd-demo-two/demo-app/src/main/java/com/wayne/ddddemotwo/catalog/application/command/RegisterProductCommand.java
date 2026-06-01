package com.wayne.ddddemotwo.catalog.application.command;

import java.math.BigDecimal;

public record RegisterProductCommand(String productId, String name, BigDecimal price) {
}
