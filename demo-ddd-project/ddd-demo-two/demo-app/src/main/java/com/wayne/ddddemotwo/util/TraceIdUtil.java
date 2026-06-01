package com.wayne.ddddemotwo.util;

import java.security.SecureRandom;

/**
 * @author pw
 * @since 2026/4/26
 */
public class TraceIdUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String getTraceId() {
        return String.valueOf(Math.abs(RANDOM.nextLong()));
    }
}
