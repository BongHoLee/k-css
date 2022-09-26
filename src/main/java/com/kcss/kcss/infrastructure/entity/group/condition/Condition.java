package com.kcss.kcss.infrastructure.entity.group.condition;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
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
        return operator.createExpressionWith(key, value);
    }
}
