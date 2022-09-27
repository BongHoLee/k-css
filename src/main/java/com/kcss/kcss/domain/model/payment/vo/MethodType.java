package com.kcss.kcss.domain.model.payment.vo;


import java.util.Arrays;

public enum MethodType {
    CARD("카드"),
    SEND("송금")
    ;

    private final String methodTypeName;

    MethodType(String methodTypeName) {
        this.methodTypeName = methodTypeName;
    }

    public String methodTypeName() {
        return methodTypeName;
    }

    public static MethodType of(String methodTypeName) {
        return Arrays.stream(MethodType.values())
                .filter(methodType -> methodType.methodTypeName().equals(methodTypeName))
                .findFirst()
                .orElseThrow();
    }
}
