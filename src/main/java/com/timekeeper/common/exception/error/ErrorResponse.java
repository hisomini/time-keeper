package com.timekeeper.common.exception.error;

public class ErrorResponse {

    private ErrorMessage message;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorMessage message) {
        this.message = message;
    }

    public ErrorMessage getErrorMessage() {
        return message;
    }
}
