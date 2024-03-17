package com.timekeeper.domain.place;

import com.timekeeper.shared.common.exception.error.ErrorMessage;

public enum PlaceError implements ErrorMessage {
    PLACE_NOT_FOUND_ERROR("존재하지 않는 장소입니다."),
    ;

    private final String message;

    PlaceError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
