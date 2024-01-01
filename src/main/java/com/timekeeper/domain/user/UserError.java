package com.timekeeper.domain.user;

import com.timekeeper.shared.common.exception.error.ErrorMessage;

public enum UserError implements ErrorMessage {
    UNAUTHORIZED_ERROR("권한이 없습니다."),
    USER_ALREADY_EXISTS_ERROR("이미 존재하는 계정입니다"),
    USER_INFO_NOT_CORRECT("아이디 또는 비밀번호가 올바르지 않습니다"),
    USER_NOT_FOUND_ERROR("존재하지 않는 사용자입니다."),
    TOKEN_NOT_VALID("토큰이 유효하지 않습니다."),
    PASSWORD_NOT_CORRECT("비밀번호가 올바르지 않습니다"),
    ;

    private final String message;

    UserError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
