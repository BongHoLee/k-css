package com.kcss.kcss.infrastructure.entity.condition;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
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
        return Expressions.booleanOperation(
                operator.ops(),
                expressions()
        );
    }

    private Expression<?>[] expressions() {
        Expression<?> parsedKey = key.expression();
        List<Expression<?>> parsedValue = value.expressionsWith();

        Expression<?>[] expressionArr = new Expression<?>[1 + parsedValue.size()];
        expressionArr[0] = parsedKey;
        for (int i = 1; i < expressionArr.length; i++) {
            expressionArr[i] = parsedValue.get(i-1);
        }

        return expressionArr;
    }
}
