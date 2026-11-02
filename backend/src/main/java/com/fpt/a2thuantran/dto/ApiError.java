package com.fpt.a2thuantran.dto;

import java.time.LocalDateTime;

/**
 * Common shape for every error response returned by this API.
 */
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String message;

    public ApiError(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
