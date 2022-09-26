package com.kcss.kcss.infrastructure.entity.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition {
    private Key key;
    private Operator operator;
    private Value value;

    public Condition(Key key, Operator operator, Value value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public BooleanExpression operate() {
        return operator.expression(key, value);
    }
}
