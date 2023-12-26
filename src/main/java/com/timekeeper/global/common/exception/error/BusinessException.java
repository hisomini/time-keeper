package com.timekeeper.global.common.exception.error;

public class BusinessException extends RuntimeException {

    private ErrorMessage message;

    public BusinessException(ErrorMessage message) {
        super(message.getMessage());
        this.message = message;
    }

    public ErrorMessage getErrorMessage() {
        return message;
    }
}
