package com.kcss.kcss.infrastructure.entity.group.vo;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


@Sql("/data/schema.sql")
@Sql("/data/data.sql")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class QslConditionTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("EQUALS and NOT_EQUALS 조건 적용")
    void 결제자의_거주지역외_송금() {
        QslCondition qslCondition1 = new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("송금"));
        QslCondition qslCondition2 = new QslCondition(QslKey.RESIDENCE, QslOperator.NOT_EQUALS, new QslValue("$region"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(qslCondition1.operate(), qslCondition2.operate())
                .fetch();

        assertThat(fetch).hasSize(37);
        for (PaymentEntity entity : fetch) {
            assertThat(entity.getMethodType()).isEqualTo("송금");
            assertThat(entity.getRegion()).isNotEqualTo(entity.getAccount().getResidence());
        }
    }

    @Test
    @DisplayName("IN and EQUALS and BETWEEN 조건 적용")
    void 서울_경기_제주_경남_30대에서_60대_패션() {
        QslCondition c1 = new QslCondition(QslKey.REGION, QslOperator.IN, new QslValue("[서울, 경기, 제주, 경남]"));
        QslCondition c2 = new QslCondition(QslKey.ITEM_CATEGORY, QslOperator.EQUALS, new QslValue("패션"));
        QslCondition c3 = new QslCondition(QslKey.AGE, QslOperator.BETWEEN, new QslValue("[30, 60]"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate(), c3.operate())
                .fetch();

        for (PaymentEntity entity : fetch) {
            System.out.println(entity.getAccount().getAge());
            assertThat(entity.getItemCategory()).isEqualTo("패션");
            assertThat(entity.getAccount().getAge()).isGreaterThanOrEqualTo(30L).isLessThanOrEqualTo(60L);
        }
    }

    @Test
    @DisplayName("BETWEEN and EQUALS 조건 적용")
    void 카드구매_100000이상_999999이하_테스트() {
        QslCondition c1 = new QslCondition(QslKey.AMOUNT, QslOperator.BETWEEN, new QslValue("[100000, 999999]"));
        QslCondition c2 = new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("카드"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate())
                .fetch();

        for (PaymentEntity entity : fetch) {
            assertThat(entity.getMethodType()).isEqualTo("카드");
        }
    }

    @Test
    void test() {
        QslCondition c1 = new QslCondition(QslKey.AMOUNT, QslOperator.BETWEEN, new QslValue("[100000, 999999]"));
        QslCondition c2 = new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("카드"));

        List<Tuple> fetch = queryFactory.select(
                        paymentEntity.count(),
                        paymentEntity.amount.sum(),
                        paymentEntity.amount.avg(),
                        paymentEntity.amount.min(),
                        paymentEntity.amount.max())
                .from(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate())
                .fetch();

        System.out.println(fetch);


    }
}
