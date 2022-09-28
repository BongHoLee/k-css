package com.kcss.kcss.domain.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcss.kcss.global.error.ErrorCode;



@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DomainErrorCode implements ErrorCode {

    // Account domain model
    NOT_VALID_AGE(1001, "A001", " not valid age"),

    // Group domain model
    NOT_STABLE_CONDITION(2001, "G001", " condition should be stable"),
    NOT_SUPPORT_VALUE_PATTERN(2002, "G002", " not support value pattern"),

    // Payment domain model
    NOT_STABLE_PAYMENT(3001, "P001", " payment should be stable"),
    NOT_VALID_AMOUNT(3002, "P002", " not valid amount")
    ;
    private final String code;
    private final String message;
    private int status;

    DomainErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
