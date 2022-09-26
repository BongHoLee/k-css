package com.kcss.kcss.infrastructure.repository;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.infrastructure.entity.condition.Condition;
import com.kcss.kcss.infrastructure.entity.condition.Key;
import com.kcss.kcss.infrastructure.entity.condition.Operator;
import com.kcss.kcss.infrastructure.entity.condition.Value;
import com.kcss.kcss.infrastructure.entity.payment.ItemCategory;
import com.kcss.kcss.infrastructure.entity.payment.MethodType;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
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
class ConditionTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("EQUALS and NOT_EQUALS 조건 적용")
    void 결제자의_거주지역외_송금() {
        Condition condition1 = new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("송금"));
        Condition condition2 = new Condition(Key.RESIDENCE, Operator.NOT_EQUALS, new Value("$region"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(condition1.operate(), condition2.operate())
                .fetch();

        assertThat(fetch).hasSize(37);
        for (PaymentEntity entity : fetch) {
            assertThat(entity.getMethodType()).isEqualTo(new MethodType("송금"));
            assertThat(entity.getRegion().getRegion()).isNotEqualTo(entity.getAccount().getResidence().getResidence());
        }
    }

    @Test
    @DisplayName("IN and EQUALS and BETWEEN 조건 적용")
    void 서울_경기_제주_경남_30대에서_60대_패션() {
        Condition c1 = new Condition(Key.REGION, Operator.IN, new Value("[서울, 경기, 제주, 경남]"));
        Condition c2 = new Condition(Key.ITEM_CATEGORY, Operator.EQUALS, new Value("패션"));
        Condition c3 = new Condition(Key.AGE, Operator.BETWEEN, new Value("[30, 60]"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate(), c3.operate())
                .fetch();

        for (PaymentEntity entity : fetch) {
            assertThat(entity.getItemCategory()).isEqualTo(new ItemCategory("패션"));
            assertThat(entity.getAccount().getAge().getAge()).isGreaterThanOrEqualTo(30).isLessThanOrEqualTo(60);
        }
    }

    @Test
    @DisplayName("BETWEEN and EQUALS 조건 적용")
    void 카드구매_100000이상_999999이하_테스트() {
        Condition c1 = new Condition(Key.AMOUNT, Operator.BETWEEN, new Value("[100000, 999999]"));
        Condition c2 = new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("카드"));

        List<PaymentEntity> fetch = queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(c1.operate(), c2.operate())
                .fetch();

        for (PaymentEntity entity : fetch) {
            assertThat(entity.getMethodType()).isEqualTo(new MethodType("카드"));
        }
    }
}
