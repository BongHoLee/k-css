package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.querydsl.core.types.dsl.Expressions.constant;
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.core.types.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class QslValue {
    @JsonValue
    private String value;

    public QslValue(String value) {
        this.value = value;
    }

    public List<Object> plainsOf(QslKey key) {
        return values().stream()
                .map(eachValue -> plainType(eachValue, key))
                .collect(toList());
    }

    // Primitive에 대한 일반 타입, QslKey에 대한 일반 타입 반환
    private Object plainType(String value, QslKey key) {
        if (value.contains("$")) {
            return QslKey.of(value.replace("$", ""));
        } else {
            return key.convertByType(value);
        }
    }

    public List<Expression<?>> expressionsOf(QslKey key) {
        return values().stream()
                .map(eachValue -> expressionType(eachValue, key))
                .collect(toList());
    }

    // Primitive에 대한 Expression 타입, QslKey에 대한 Expression 타입 반환
    private Expression<?> expressionType(String value, QslKey key) {
        if (value.contains("$")) {
            return QslKey.of(value.replace("$", "")).expression();
        } else {
            return constant(key.convertByType(value));
        }
    }

    private List<String> values() {
        List<String> valueList = new ArrayList<>();
        if (value.startsWith("[") && value.endsWith("]")) {
            valueList.addAll(Arrays.asList(value.replace("[", "")
                    .replace("]", "")
                    .replace(" ", "")
                    .split(",")));
        } else {
            valueList.add(value);
        }

        return valueList;
    }
}
