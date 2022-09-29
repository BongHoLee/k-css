package com.kcss.kcss.domain.model.group.vo;

import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import java.util.Arrays;

public enum Operator {
    IN("in"),
    NOT_IN("not in"),
    EQUALS("equals"),
    NOT_EQUALS("not equals"),
    BETWEEN("between"),
    LARGER_THAN("larger than"),
    LESS_THAN("larger than")
    ;
    private final String operatorName;

    Operator(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public static Operator of(String operatorName) {
        return Arrays.stream(Operator.values())
                .filter(operator -> operator.getOperatorName().equals(operatorName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support operator : " + operatorName, DomainErrorCode.NOT_SUPPORT_OPERATOR));
    }
}
