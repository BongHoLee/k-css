package com.kcss.kcss.application.dto;


import com.kcss.kcss.domain.model.group.Statistics;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StatisticsDTO {
    private Long groupId;
    private Long count;
    private Double totalAmount;
    private Double avgAmount;
    private Double maxAmount;
    private Double minAmount;


    public static StatisticsDTO of(Statistics statistics) {
        return StatisticsDTO.builder()
                .groupId(statistics.getGroup().getId())
                .count(statistics.getCount())
                .totalAmount(statistics.getTotalAmount())
                .avgAmount(statistics.getAvgAmount())
                .minAmount(statistics.getMinAmount())
                .maxAmount(statistics.getMaxAmount())
                .build();
    }
}
