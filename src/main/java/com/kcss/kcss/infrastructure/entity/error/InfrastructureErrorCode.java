package com.kcss.kcss.infrastructure.entity.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcss.kcss.global.error.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InfrastructureErrorCode implements ErrorCode{

    NOT_VALID_ID(5001, "I001", " payment id cannot be null"),
    ;

    private final String code;
    private final String message;
    private int status;

    InfrastructureErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }
}
