package com.kcss.kcss.infrastructure.entity.group.vo;


import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QslKeyTest {
    @Test
    @DisplayName("QslKey 타입과 일치하는 QExpression 타입 생성 테스트")
    void QslKey_타입과_일치하는_Expression_타입_생성_테스트() {
        assertThat(QslKey.REGION.expression()).isInstanceOf(paymentEntity.region.getClass());
        assertThat(QslKey.RESIDENCE.expression()).isInstanceOf(accountEntity.residence.getClass());
        assertThat(QslKey.METHOD_TYPE.expression()).isInstanceOf(paymentEntity.methodType.getClass());
        assertThat(QslKey.ITEM_CATEGORY.expression()).isInstanceOf(paymentEntity.itemCategory.getClass());
        assertThat(QslKey.ACCOUNT_ID.expression()).isInstanceOf(accountEntity.id.getClass());
        assertThat(QslKey.AMOUNT.expression()).isInstanceOf(paymentEntity.amount.getClass());
        assertThat(QslKey.AGE.expression()).isInstanceOf(accountEntity.age.getClass());

    }

    @Test
    @DisplayName("QslKey 타입에 해당하는 일반 타입 반환 테스트")
    void QslKey타입에_해당하는_일반타입_반환_테스트() {
        assertThat(QslKey.AGE.convertByType("30")).isInstanceOf(Long.class);
        assertThat(QslKey.AMOUNT.convertByType("30.0")).isInstanceOf(Double.class);
        assertThat(QslKey.REGION.convertByType("제주")).isInstanceOf(String.class);
        assertThat(QslKey.ITEM_CATEGORY.convertByType("패션")).isInstanceOf(String.class);
        assertThat(QslKey.METHOD_TYPE.convertByType("송금")).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("QslKey 이름과 일치하는 타입 생성 테스트")
    void 문자열_이름과_일치하는_QslKey_타입_생성_테스트() {
        assertThat(QslKey.of("residence")).isInstanceOf(QslKey.RESIDENCE.getClass());
        assertThat(QslKey.of("methodType")).isInstanceOf(QslKey.METHOD_TYPE.getClass());
        assertThat(QslKey.of("itemCategory")).isInstanceOf(QslKey.ITEM_CATEGORY.getClass());
        assertThat(QslKey.of("region")).isInstanceOf(QslKey.REGION.getClass());
        assertThat(QslKey.of("accountId")).isInstanceOf(QslKey.ACCOUNT_ID.getClass());
        assertThat(QslKey.of("amount")).isInstanceOf(QslKey.AMOUNT.getClass());
        assertThat(QslKey.of("age")).isInstanceOf(QslKey.AGE.getClass());
    }
}