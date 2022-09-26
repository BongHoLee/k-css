package com.kcss.kcss.infrastructure.entity.group;

import com.kcss.kcss.infrastructure.common.converter.ConditionJsonConverter;
import com.kcss.kcss.infrastructure.entity.group.condition.Condition;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
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
    private List<Condition> conditions;
}
