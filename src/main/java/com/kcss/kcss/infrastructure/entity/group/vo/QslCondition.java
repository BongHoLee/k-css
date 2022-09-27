package com.kcss.kcss.infrastructure.entity.group.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class QslCondition {
    private QslKey key;
    private QslOperator operator;
    private QslValue value;

    public QslCondition(QslKey key, QslOperator operator, QslValue value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public BooleanExpression operate() {
        return operator.createExpressionWith(key, value);
    }
}
