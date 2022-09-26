package com.kcss.kcss.infrastructure.entity.condition;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.function.BiFunction;

public enum Operator {
    // collection operating type : Expressions.constant(Arrays.asList(PLAINT_TYPE, PLAIN_TYPE))
    IN("in",
            (key, value) -> Expressions.booleanOperation(Ops.IN, key.expression(), Expressions.constant(value.plainsOf(key)))),

    NOT_IN("not in",
            (key, value) -> Expressions.booleanOperation(Ops.NOT_IN, key.expression(), Expressions.constant(value.plainsOf(key)))),

    // general operating Type : Expressions[key's expression, value's expression, value's expression]
    EQUALS("equals",
            (key, value) -> Expressions.booleanOperation(Ops.EQ,generalOperatingExpressionsOf(key, value))),
    NOT_EQUALS("not equals",
            (key, value) -> Expressions.booleanOperation(Ops.NE, generalOperatingExpressionsOf(key, value))),

    BETWEEN("between",
            (key, value) -> Expressions.booleanOperation(Ops.BETWEEN, generalOperatingExpressionsOf(key, value))),

    LARGER_THAN("larger than",
            (key, value) -> Expressions.booleanOperation(Ops.GT, generalOperatingExpressionsOf(key, value))),

    LESS_THAN("larger than",
            (key, value) ->  Expressions.booleanOperation(Ops.LT,generalOperatingExpressionsOf(key, value))),
    ;
    private String operatorName;

    private BiFunction<Key, Value, BooleanExpression> expressionFunction;

    Operator(String operatorName,  BiFunction<Key, Value, BooleanExpression> expressionFunction) {
        this.operatorName = operatorName;
        this.expressionFunction = expressionFunction;
    }

    public BooleanExpression expression(Key key, Value value) {
        return this.expressionFunction.apply(key, value);
    }

    public String getOperatorName() {
        return operatorName;
    }

    private static Expression<?>[] generalOperatingExpressionsOf(Key key, Value value) {
        List<Expression<?>> expressions = value.expressionsOf(key);
        expressions.add(0, key.expression());
        return expressions.toArray(new Expression[0]);
    }
}
