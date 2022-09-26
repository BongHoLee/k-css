package com.kcss.kcss.infrastructure.entity.group.condition;

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
public class Value {
    @JsonValue
    private String value;

    public Value(String value) {
        this.value = value;
    }

    public List<Object> plainsOf(Key key) {
        return values().stream()
                .map(eachValue -> plainType(eachValue, key))
                .collect(toList());
    }

    private Object plainType(String value, Key key) {
        if (value.contains("$")) {
            return Key.of(value.replace("$", ""));
        } else {
            return key.convertByType(value);
        }
    }

    public List<Expression<?>> expressionsOf(Key key) {
        return values().stream()
                .map(eachValue -> expressionType(eachValue, key))
                .collect(toList());
    }

    private Expression<?> expressionType(String value, Key key) {
        if (value.contains("$")) {
            return Key.of(value.replace("$", "")).expression();
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
