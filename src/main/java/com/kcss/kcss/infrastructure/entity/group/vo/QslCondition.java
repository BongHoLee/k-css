package com.kcss.kcss.infrastructure.entity.group.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class QslCondition {
    private QslKey key;
    private QslOperator operator;
    private QslValue value;

    @Builder
    public QslCondition(QslKey key, QslOperator operator, QslValue value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public BooleanExpression operate() {
        return operator.createExpressionWith(key, value);
    }

    public static QslCondition from(Condition condition) {
        return QslCondition.builder()
                .key(QslKey.of(condition.getKey().getKeyName()))
                .operator(QslOperator.of(condition.getOperator().getOperatorName()))
                .value(new QslValue(condition.getValue().getValue()))
                .build();
    }

    public Condition convert() {
        return Condition.builder()
                .key(Key.valueOf(this.key.name()))
                .operator(Operator.valueOf(this.operator.name()))
                .value(new Value(this.value.getValue()))
                .build();
    }
}
