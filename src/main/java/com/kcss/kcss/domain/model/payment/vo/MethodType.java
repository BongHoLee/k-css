package com.kcss.kcss.domain.model.payment.vo;


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
}
