package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.querydsl.core.types.dsl.Expressions.booleanOperation;
import static com.querydsl.core.types.dsl.Expressions.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public enum QslOperator {
    // collection operating type : Expressions.constant(Arrays.asList(PLAINT_TYPE, PLAIN_TYPE))
    IN("in",
            (key, value) -> booleanOperation(Ops.IN, key.expression(), constant(value.plainsOf(key)))),

    NOT_IN("not in",
            (key, value) -> booleanOperation(Ops.NOT_IN, key.expression(), constant(value.plainsOf(key)))),

    // general operating Type : Expressions[key's expression, value's expression, value's expression]
    EQUALS("equals",
            (key, value) -> booleanOperation(Ops.EQ, generalOperatingExpressionsOf(key, value))),

    NOT_EQUALS("not equals",
            (key, value) -> booleanOperation(Ops.NE, generalOperatingExpressionsOf(key, value))),

    BETWEEN("between",
            (key, value) -> booleanOperation(Ops.BETWEEN, generalOperatingExpressionsOf(key, value))),

    LARGER_THAN("larger than",
            (key, value) -> booleanOperation(Ops.GT, generalOperatingExpressionsOf(key, value))),

    LESS_THAN("larger than",
            (key, value) -> booleanOperation(Ops.LT, generalOperatingExpressionsOf(key, value))),
    ;
    private String operatorName;

    private BiFunction<QslKey, QslValue, BooleanExpression> expressionFunction;

    QslOperator(String operatorName, BiFunction<QslKey, QslValue, BooleanExpression> expressionFunction) {
        this.operatorName = operatorName;
        this.expressionFunction = expressionFunction;
    }

    public BooleanExpression createExpressionWith(QslKey qslKey, QslValue value) {
        return this.expressionFunction.apply(qslKey, value);
    }

    @JsonValue
    public String getOperatorName() {
        return operatorName;
    }

    public static QslOperator of(String operatorName) {
        return Arrays.stream(QslOperator.values())
                .filter(key -> key.getOperatorName().equals(operatorName))
                .findFirst()
                .orElseThrow();
    }

    private static Expression<?>[] generalOperatingExpressionsOf(QslKey qslKey, QslValue value) {
        List<Expression<?>> expressions = value.expressionsOf(qslKey);
        expressions.add(0, qslKey.expression());
        return expressions.toArray(new Expression[0]);
    }
}
