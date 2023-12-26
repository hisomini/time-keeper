package com.timekeeper.global.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final int errorCode;
    private final String errorMessage;

}
