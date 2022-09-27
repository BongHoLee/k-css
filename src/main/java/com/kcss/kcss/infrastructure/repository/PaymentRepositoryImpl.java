package com.kcss.kcss.infrastructure.repository;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.kcss.kcss.infrastructure.entity.group.condition.Condition;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    public List<PaymentEntity> findAll(Condition condition) {
        return queryFactory.selectFrom(paymentEntity)
                .innerJoin(paymentEntity.account, accountEntity)
                .where(paymentEntity.region.ne(accountEntity.residence))
                .fetch();
    }
}
