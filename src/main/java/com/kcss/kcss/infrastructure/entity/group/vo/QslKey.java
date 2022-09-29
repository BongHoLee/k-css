package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.querydsl.core.types.Expression;
import java.util.Arrays;
import java.util.function.Function;

// Key에 대한 Expression과 Type으로 변환해주는 책임
public enum QslKey {
    REGION("region", paymentEntity.region, value -> Region.of(value).regionName()),
    RESIDENCE("residence", accountEntity.residence, value -> Residence.of(value).residenceName()),
    METHOD_TYPE("methodType", paymentEntity.methodType, value -> MethodType.of(value).methodTypeName()),
    ITEM_CATEGORY("itemCategory", paymentEntity.itemCategory, value -> ItemCategory.of(value).categoryName()),
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
