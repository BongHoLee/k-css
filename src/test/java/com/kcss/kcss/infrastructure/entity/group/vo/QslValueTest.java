package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QslValueTest {

    @Test
    @DisplayName("$연산자 plainsOf 호출 시 QslKey 타입 반환 테스트")
    void $_연산자_QslKey_타입_반환_테스트() {
        QslValue value = new QslValue("$region");
        List<Object> objects = value.plainsOf(QslKey.REGION);
        assertThat(objects).hasSize(1);
        assertThat(objects.get(0)).isInstanceOf(QslKey.REGION.getClass());
    }

    @Test
    @DisplayName("[일반타입, 일반타입] plainsOf 호출 시 리스트 일반타입 반환 테스트")
    void 다중_연산자_일반타입_plainsOf_리스트반환_테스트() {
        QslValue value = new QslValue("[제주, 서울]");
        List<Object> objects = value.plainsOf(QslKey.RESIDENCE);
        assertThat(objects).hasSize(2);
        assertThat(objects.get(0)).hasToString("제주");
        assertThat(objects.get(1)).hasToString("서울");
    }

    @Test
    @DisplayName("[$연산자, 일반타입] plainsOf 호출 시 리스트 [QslKey타입, 일반타입] 반환 테스트")
    void 다중_연산자_$연산자_일반타입_plainsOf_리스트반환_테스트() {
        QslValue value = new QslValue("[$region, 서울]");
        List<Object> objects = value.plainsOf(QslKey.REGION);
        assertThat(objects).hasSize(2);
        assertThat(objects.get(0)).isInstanceOf(QslKey.REGION.getClass());
        assertThat(objects.get(1)).hasToString("서울");
    }

    @Test
    @DisplayName("$연산자 expressionOf 호출 시 해당 QExpression 타입 반환 테스트")
    void $_연산자_Expression_타입_반환_테스트() {
        QslValue value = new QslValue("$region");
        List<Expression<?>> expressions = value.expressionsOf(QslKey.REGION);
        assertThat(expressions.get(0)).isInstanceOf(paymentEntity.region.getClass());
    }

    @Test
    @DisplayName("[일반타입, 일반타입] expressionOf 호출 시 Constant QExpression 타입 반환 테스트")
    void 다중_연산자_일반_타입_Expression_타입_반환_테스트() {
        QslValue value = new QslValue("[제주, 서울]");
        List<Expression<?>> expressions = value.expressionsOf(QslKey.REGION);
        assertThat(expressions.get(0)).isInstanceOf(ConstantImpl.class);
        assertThat(expressions.get(1)).isInstanceOf(ConstantImpl.class);
    }
}