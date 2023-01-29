package com.bicycle.project.oauthlogin.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.bicycle.project.oauthlogin.config.RegularResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
public class RegularResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String message;

    private final int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer etc;

    public RegularResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    public RegularResponse(T result ,int etc) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
        this.etc = etc; //총글개수

    }

    // 요청에 실패한 경우
    public RegularResponse(RegularResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
