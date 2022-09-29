package com.kcss.kcss.infrastructure.entity.group.vo;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QslOperatorTest {

    @Test
    @DisplayName("METHOD_TYPE의 QExpresion과 일반 타입 QslValue에 맞는 피연산자 생성 테스트")
    void METHOD_TYPE_and_송금_Expression_생성_테스트() {
        QslOperator operator = QslOperator.EQUALS;
        BooleanExpression expression = operator.createExpressionWith(QslKey.METHOD_TYPE, new QslValue("송금"));
        assertThat(expression).hasToString("paymentEntity.methodType = 송금");
    }

    @Test
    @DisplayName("RESIDENCE의 QExpresion과 $연산 타입 QslValue에 맞는 피연산자 생성 테스트")
    void RESIDENCE_and_$region_Expression_생성_테스트() {
        QslOperator operator = QslOperator.EQUALS;
        BooleanExpression expression = operator.createExpressionWith(QslKey.RESIDENCE, new QslValue("$region"));
        assertThat(expression).hasToString("accountEntity.residence = paymentEntity.region");
    }

    @Test
    @DisplayName("RESIDENCE의 QExpresion과 [] 타입 QslValue에 맞는 피연산자 생성 테스트")
    void RESIDENCE_and_다중_피연산자_Expression_생성_테스트() {
        QslOperator operator = QslOperator.IN;
        BooleanExpression expression = operator.createExpressionWith(QslKey.RESIDENCE, new QslValue("[제주, 서울]"));
        assertThat(expression).hasToString("accountEntity.residence in [제주, 서울]");
    }
}