package com.kcss.kcss.infrastructure.repository.group;

import static com.kcss.kcss.infrastructure.entity.account.QAccountEntity.accountEntity;
import static com.kcss.kcss.infrastructure.entity.payment.QPaymentEntity.paymentEntity;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.Statistics;
import com.kcss.kcss.domain.repository.GroupRepository;
import com.kcss.kcss.infrastructure.entity.group.GroupEntity;
import com.kcss.kcss.infrastructure.entity.group.StatisticsDO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final JpaGroupRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    public GroupRepositoryImpl(JpaGroupRepository jpaRepository, JPAQueryFactory queryFactory) {
        this.jpaRepository = jpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public Group save(Group group) {
        GroupEntity saved = jpaRepository.save(GroupEntity.from(group));
        return saved.convert();
    }

    @Override
    public Optional<Group> findById(Long id) {
        Optional<GroupEntity> findEntity = jpaRepository.findById(id);
        return findEntity.map(GroupEntity::convert);
    }

    @Override
    public Statistics statisticsOf(Group group) {

        StatisticsDO statisticsDO = queryFactory.select(
                Projections.constructor(StatisticsDO.class,
                        paymentEntity.count(),
                        paymentEntity.amount.sum(),
                        paymentEntity.amount.avg(),
                        paymentEntity.amount.min(),
                        paymentEntity.amount.max()))
                .from(paymentEntity)
                .innerJoin(paymentEntity.accountEntity, accountEntity)
                .where(GroupEntity.from(group).expressionForCondition())
                .fetch().get(0);

        return Statistics.builder()
                .group(group)
                .count(statisticsDO.getCount())
                .totalAmount(statisticsDO.getTotalAmount())
                .avgAmount(statisticsDO.getAvgAmount())
                .maxAmount(statisticsDO.getAvgAmount())
                .minAmount(statisticsDO.getMinAmount())
                .build();
    }
}
