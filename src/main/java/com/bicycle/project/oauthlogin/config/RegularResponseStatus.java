package com.bicycle.project.oauthlogin.config;

import lombok.Getter;

@Getter
public enum RegularResponseStatus {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    RegularResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
