package com.kcss.kcss.infrastructure.entity.group;

import com.kcss.kcss.infrastructure.common.converter.ConditionJsonConverter;
import com.kcss.kcss.infrastructure.entity.group.vo.QslCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pgroup")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId")
    private Long id;

    @Column(name = "information")
    private String description;

    @Convert(converter = ConditionJsonConverter.class)
    @Column(name = "conditions", columnDefinition = "json")
    private List<QslCondition> qslConditions;

    @Builder
    public GroupEntity(Long id, String description, List<QslCondition> qslConditions) {
        this.id = id;
        this.description = description;
        this.qslConditions = qslConditions;
    }

    public BooleanExpression[] expressionForCondition() {
        return qslConditions.stream()
                .map(QslCondition::operate)
                .toArray(BooleanExpression[]::new);
    }
}
