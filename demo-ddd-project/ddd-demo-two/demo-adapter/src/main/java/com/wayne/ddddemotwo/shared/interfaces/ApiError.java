package com.wayne.ddddemotwo.shared.interfaces;

import java.time.LocalDateTime;

public record ApiError(String message, LocalDateTime timestamp) {
}
