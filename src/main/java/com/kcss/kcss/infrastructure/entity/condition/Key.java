package com.kcss.kcss.infrastructure.entity.condition;

import com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity;
import com.querydsl.core.types.Expression;
import java.util.Arrays;

public enum Key {
    REGION("region", QPaymentEntity.paymentEntity.region.region),
    RESIDENCE("residence", QPaymentEntity.paymentEntity.account.residence.residence),
    AMOUNT("amount", QPaymentEntity.paymentEntity.amount.amount),
    METHOD_TYPE("methodType", QPaymentEntity.paymentEntity.methodType.methodType),
    ITEM_CATEGORY("itemCategory", QPaymentEntity.paymentEntity.itemCategory.itemCategory),
    ACCOUNT_ID("accountId", QPaymentEntity.paymentEntity.account.id),
    AGE("age", QPaymentEntity.paymentEntity.account.id)
    ;

    private final String keyName;
    private final Expression<?> expression;

    Key(String keyName, Expression<?> expression) {
        this.keyName = keyName;
        this.expression = expression;
    }

    public Expression<?> expression() {
        return this.expression;
    }

    public static Key of(String keyName) {
        return Arrays.stream(Key.values())
                .filter(key -> key.getKeyName().equals(keyName))
                .findFirst()
                .orElseThrow();
    }

    public String getKeyName() {
        return this.keyName;
    }
}
