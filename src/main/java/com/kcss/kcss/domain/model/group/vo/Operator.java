package com.kcss.kcss.domain.model.group.vo;

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
}
