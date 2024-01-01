package com.timekeeper.shared.common.exception.error;

public class ErrorResponse {

    private String message;

    public ErrorResponse(ErrorMessage message) {
        this.message = message.getMessage();
    }

    public String getErrorMessage() {
        return message;
    }
}
