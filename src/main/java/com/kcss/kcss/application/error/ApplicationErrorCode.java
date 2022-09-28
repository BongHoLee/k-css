package com.kcss.kcss.application.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcss.kcss.global.error.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationErrorCode implements ErrorCode{

    INVALID_INPUT_VALUE(400, "P001", " INVALID INPUT VALUE"),
    METHOD_NOT_ALLOWED(405, "P002", " NOT SUPPORT HTTP METHOD"),
    INTERNAL_SERVER_ERROR(500, "P004", "SERVER ERROR"),
    NO_CONTENT(204, "P005", "NO CONTENTS")
    ;

    private final String code;
    private final String message;
    private int status;

    ApplicationErrorCode(final int status, final String code, final String message) {
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
