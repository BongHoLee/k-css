package com.kcss.kcss.infrastructure.repository;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.kcss.kcss.infrastructure.entity.condition.Condition;
import com.kcss.kcss.infrastructure.entity.condition.Key;
import com.kcss.kcss.infrastructure.entity.condition.Operator;
import com.kcss.kcss.infrastructure.entity.condition.Value;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class QueryDSLTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    void findAllTest() {
        Condition condition1 = new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"));
        Condition condition2 = new Condition(Key.RESIDENCE, Operator.NOT_EQUALS, new Value("$region"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(condition1.operate(), condition2.operate())
                .fetch();

        System.out.println(fetch);
    }

    @Test
    void test2() {
        Condition c1 = new Condition(Key.REGION, Operator.IN, new Value("[서울, 경기, 제주, 경남]"));
        Condition c2 = new Condition(Key.ITEM_CATEGORY, Operator.EQUALS, new Value("패션"));
        Condition c3 = new Condition(Key.AGE, Operator.BETWEEN, new Value("[30, 60]"));


        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate(), c3.operate())
                .fetch();

        System.out.println(fetch);
    }

    @Test
    void test3() {
        Condition c1 = new Condition(Key.AMOUNT, Operator.BETWEEN, new Value("[100000, 999999]"));
        Condition c2 = new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("카드"));


        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate())
                .fetch();

        System.out.println(fetch);
    }


}
