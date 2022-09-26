package com.kcss.kcss.infrastructure.entity.group.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
        return operator.expression(key, value);
    }
}
