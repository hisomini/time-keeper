package com.timekeeper.user.command.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JoinSource {
    EMAIL("EMAIL", "이메일"),
    KAKAO("KAKAO", "카카오"),
    NAVER("NAVER", "네이버");
    private final String key;
    private final String title;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
