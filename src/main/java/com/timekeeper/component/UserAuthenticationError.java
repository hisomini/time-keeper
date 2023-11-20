package com.timekeeper.component;


import com.timekeeper.common.exception.error.ErrorMessage;

public enum UserAuthenticationError implements ErrorMessage {
    INTERNAL_SERVER_ERROR("시스템 오류가 발생했습니다. 관리자에게 문의해주세요."),
    USER_INFO_NOT_CORRECT("아이디 또는 비밀번호가 올바르지 않습니다"),
    ;
    private final String message;

    UserAuthenticationError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
