package com.kcss.kcss.infrastructure.entity.group;

import static java.util.stream.Collectors.toList;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.infrastructure.converter.ConditionJsonConverter;
import com.kcss.kcss.infrastructure.entity.error.InfrastructureErrorCode;
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
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@Table(name = "pgroup")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class GroupEntity {

    @Id
    @Column(name = "groupId")
    private Long id;

    @Column(name = "information")
    private String description;

    @Convert(converter = ConditionJsonConverter.class)
    @Column(name = "conditions", columnDefinition = "json")
    private List<QslCondition> conditions;

    @Builder
    public GroupEntity(Long id, String description, List<QslCondition> conditions) {
        validation(id);
        this.id = id;
        this.description = description;
        this.conditions = conditions;
    }

    private void validation(Long id) {
        if (id == null) {
            log.error("payment id cannot be null");
            throw new BusinessException(InfrastructureErrorCode.NOT_VALID_ID);
        }
    }
    public BooleanExpression[] expressionForCondition() {
        return conditions.stream()
                .map(QslCondition::operate)
                .toArray(BooleanExpression[]::new);
    }

    public static GroupEntity from(Group group) {
        return GroupEntity.builder()
                .id(group.getId())
                .conditions(group.getConditions().stream().map(QslCondition::from).collect(toList()))
                .description(group.getDescription())
                .build();
    }

    public Group convert() {
        return Group.builder()
                .id(id)
                .conditions(conditions.stream().map(QslCondition::convert).collect(toList()))
                .description(description)
                .build();
    }
}
