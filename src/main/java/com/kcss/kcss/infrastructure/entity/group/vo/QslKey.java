package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.core.types.Expression;
import java.util.Arrays;
import java.util.function.Function;

// Key에 대한 Expression과 Type으로 변환해주는 책임
public enum QslKey {
    REGION("region", paymentEntity.region, value -> value),
    RESIDENCE("residence", paymentEntity.account.residence, value -> value),
    METHOD_TYPE("methodType", paymentEntity.methodType, value -> value),
    ITEM_CATEGORY("itemCategory", paymentEntity.itemCategory, value -> value),
    ACCOUNT_ID("accountId", accountEntity.id, Long::parseLong),
    AMOUNT("amount", paymentEntity.amount, Double::parseDouble),
    AGE("age", accountEntity.age, Long::parseLong)
    ;

    private final String keyName;
    private final Expression<?> expression;

    private final Function<String, Object> converter;

    QslKey(String keyName, Expression<?> expression, Function<String, Object> converter) {
        this.keyName = keyName;
        this.expression = expression;
        this.converter = converter;
    }

    public Expression<?> expression() {
        return this.expression;
    }

    public Object convertByType(String value) {
        return this.converter.apply(value);
    }

    @JsonValue
    public String getKeyName() {
        return this.keyName;
    }

    public static QslKey of(String keyName) {
        return Arrays.stream(QslKey.values())
                .filter(key -> key.getKeyName().equals(keyName))
                .findFirst()
                .orElseThrow();
    }
}
