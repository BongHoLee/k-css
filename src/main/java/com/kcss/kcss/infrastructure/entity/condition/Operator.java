package com.kcss.kcss.infrastructure.entity.condition;

import com.querydsl.core.types.Ops;

public enum Operator {
    IN("in", Ops.IN),
    EQUALS("equals", Ops.EQ),
    NOT_EQUALS("not equals", Ops.NE),
    BETWEEN("between",  Ops.BETWEEN),
    LARGER_THAN("larger than", Ops.GT),
    LESS_THAN("larger than", Ops.LT),
    ;

    private String name;
    private Ops ops;

    Operator(String name, Ops ops) {
        this.name = name;
        this.ops = ops;
    }

    public Ops ops() {
        return ops;
    }

    public String getName() {
        return name;
    }
}
