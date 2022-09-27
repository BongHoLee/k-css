package com.kcss.kcss.domain.model.group;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Statistics {
    private final Group group;
    private final Long count;
    private final Double totalAmount;
    private final Double avgAmount;
    private final Double minAmount;
    private final Double maxAmount;

    @Builder
    public Statistics(Group group, Long count, Double totalAmount, Double avgAmount, Double minAmount, Double maxAmount) {
        this.group = group;
        this.count = count;
        this.totalAmount = totalAmount;
        this.avgAmount = avgAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
