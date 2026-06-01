package com.wayne.ddddemo.catalog.application.dto;

import java.math.BigDecimal;

public record ProductDto(String id, String name, BigDecimal price, boolean active, String traceId) {
}
