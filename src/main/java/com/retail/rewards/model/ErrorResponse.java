package com.retail.rewards.model;

import java.time.LocalDateTime;

public record ErrorResponse(int statusCode, String error, String errorMessage, LocalDateTime timestamp) {

    public ErrorResponse(int statusCode, String error, String errorMessage) {
        this(statusCode, error, errorMessage, LocalDateTime.now());
    }
}
