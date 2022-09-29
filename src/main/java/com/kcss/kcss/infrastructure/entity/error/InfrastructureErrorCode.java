package com.kcss.kcss.infrastructure.entity.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcss.kcss.global.error.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InfrastructureErrorCode implements ErrorCode{

    NOT_VALID_ID(5001, "I001", " id cannot be null"),
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
        return this.message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public int getStatus() {
        return this.status;
    }
}
