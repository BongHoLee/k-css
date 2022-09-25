package com.kcss.kcss.infrastructure.entity.condition;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.ArrayList;
import java.util.List;

public class Value {
    private String value;

    public Value(String value) {
        this.value = value;
    }

    public List<Expression<?>> expressionsWith() {
        return expressionsWith(this.value);
    }


    private List<Expression<?>> expressionsWith(String value) {
        List<Expression<?>> expressions = new ArrayList<>();
        if (value.startsWith("[") && value.endsWith("]")) {
            expressions.addAll(multipleValues(value));
        } else if (value.contains("$")) {
            expressions.add(keyParameter(value));
        } else {
            expressions.add(plainValue(value));
        }

        return expressions;
    }

    private Expression<?> plainValue(String value) {
        return Expressions.constant(value);
    }

    private Expression<?> keyParameter(String value) {
        return Key.of(value.replace("$", "")).expression();
    }

    private List<Expression<?>> multipleValues(String value) {
        List<Expression<?>> expressions = new ArrayList<>();

        String[] values = value.replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split(",");

        for (String each : values) {
            expressions.addAll(expressionsWith(each));
        }

        return expressions;
    }

}
