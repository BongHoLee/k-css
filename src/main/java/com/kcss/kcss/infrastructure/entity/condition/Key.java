package com.kcss.kcss.infrastructure.entity.condition;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.querydsl.core.types.Expression;
import java.util.Arrays;
import java.util.function.Function;

// Key에 대한 Expression과 Type으로 변환해주는 책임
public enum Key {
    REGION("region", paymentEntity.region.region, value -> value),
    RESIDENCE("residence", paymentEntity.account.residence.residence, value -> value),
    METHOD_TYPE("methodType", paymentEntity.methodType.methodType, value -> value),
    ITEM_CATEGORY("itemCategory", paymentEntity.itemCategory.itemCategory, value -> value),
    ACCOUNT_ID("accountId", accountEntity.id, Long::parseLong),
    AMOUNT("amount", paymentEntity.amount.amount, Double::parseDouble),
    AGE("age", accountEntity.age.age, Long::parseLong)
    ;

    private final String keyName;
    private final Expression<?> expression;

    private final Function<String, Object> converter;

    Key(String keyName, Expression<?> expression, Function<String, Object> converter) {
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

    public String getKeyName() {
        return this.keyName;
    }

    public static Key of(String keyName) {
        return Arrays.stream(Key.values())
                .filter(key -> key.getKeyName().equals(keyName))
                .findFirst()
                .orElseThrow();
    }
}
