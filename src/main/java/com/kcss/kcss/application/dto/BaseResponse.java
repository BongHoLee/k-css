package com.kcss.kcss.application.dto;

import com.kcss.kcss.global.error.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse {
    private boolean success;
    private String errorMessage;

    public BaseResponse(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static BaseResponse success() {
        return new BaseResponse(true, null);
    }

    public static BaseResponse errorOf(ErrorCode errorCode) {
        return new BaseResponse(false, errorCode.getMessage());
    }
}