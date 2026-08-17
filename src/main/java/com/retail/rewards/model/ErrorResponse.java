package com.retail.rewards.model;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int statusCode;
    private String error;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorResponse(int statusCode, String error, String errorMessage) {
        this.statusCode = statusCode;
        this.error = error;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
