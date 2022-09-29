package com.kcss.kcss.domain.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcss.kcss.global.error.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DomainErrorCode implements ErrorCode {

    // Account domain model
    NOT_VALID_AGE(1001, "A001", " not valid age"),
    NOT_SUPPORT_RESIDENCE(1002, "A002", " not support residence"),

    // Group domain model
    NOT_STABLE_CONDITION(2001, "G001", " condition should be stable"),
    NOT_SUPPORT_VALUE_PATTERN(2002, "G002", " not support value pattern"),
    NOT_SUPPORT_KEY(2003, "G002", " not support key"),
    NOT_SUPPORT_OPERATOR(2004, "G002", " not support operator"),

    // Payment domain model
    NOT_STABLE_PAYMENT(3001, "P001", " payment should be stable"),
    NOT_VALID_AMOUNT(3002, "P002", " not valid amount"),
    NOT_SUPPORT_REGION(3003, "P003", " not support region"),
    NOT_SUPPORT_METHOD_TYPE(3004, "P004", " not support method type"),
    NOT_SUPPORT_CATEGORY(3005, "P005", " not support category"),
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
