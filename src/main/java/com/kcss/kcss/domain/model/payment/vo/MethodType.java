package com.kcss.kcss.domain.model.payment.vo;


import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
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

    public static MethodType kor(String methodTypeName) {
        return Arrays.stream(MethodType.values())
                .filter(methodType -> methodType.methodTypeName().equals(methodTypeName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support method type : " + methodTypeName, DomainErrorCode.NOT_SUPPORT_METHOD_TYPE));
    }

    public static MethodType en(String methodTypeName) {
        return Arrays.stream(MethodType.values())
                .filter(methodType -> methodType.name().equals(methodTypeName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support method type : " + methodTypeName, DomainErrorCode.NOT_SUPPORT_METHOD_TYPE));
    }
}
