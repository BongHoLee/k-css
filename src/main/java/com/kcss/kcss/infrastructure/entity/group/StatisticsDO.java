package com.kcss.kcss.infrastructure.entity.group;

import com.kcss.kcss.domain.model.group.Group;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StatisticsDO {
    private final Long count;
    private final Double totalAmount;
    private final Double avgAmount;
    private final Double minAmount;
    private final Double maxAmount;

    @Builder
    public StatisticsDO(Long count, Double totalAmount, Double avgAmount, Double minAmount, Double maxAmount) {
        this.count = count;
        this.totalAmount = totalAmount;
        this.avgAmount = avgAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
