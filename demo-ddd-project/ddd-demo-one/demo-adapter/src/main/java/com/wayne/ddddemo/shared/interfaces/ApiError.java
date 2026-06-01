package com.wayne.ddddemo.shared.interfaces;

import java.time.LocalDateTime;

public record ApiError(String message, LocalDateTime timestamp) {
}
