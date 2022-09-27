package com.kcss.kcss.domain.model.group.vo;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode
@Getter
public class Condition {
    private final Key key;
    private final Operator operator;
    private final Value value;

    @Builder
    public Condition(Key key, Operator operator, Value value) {
        validation(key, operator, value);
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    private void validation(Key key, Operator operator, Value value) {
        if (key == null || operator == null || value == null) {
            log.error("Condition should be stable");
            throw new IllegalArgumentException("Condition should be stable");
        }
    }
}
