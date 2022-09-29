package com.kcss.kcss.infrastructure.entity.group;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.group.Group;
import com.kcss.kcss.domain.model.group.vo.Condition;
import com.kcss.kcss.domain.model.group.vo.Key;
import com.kcss.kcss.domain.model.group.vo.Operator;
import com.kcss.kcss.domain.model.group.vo.Value;
import com.kcss.kcss.infrastructure.entity.group.vo.QslCondition;
import com.kcss.kcss.infrastructure.entity.group.vo.QslKey;
import com.kcss.kcss.infrastructure.entity.group.vo.QslOperator;
import com.kcss.kcss.infrastructure.entity.group.vo.QslValue;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupEntityTest {

    @Test
    @DisplayName("Group에서 GroupEntity로의 변환 테스트")
    void Group_to_GroupEntity_변환_테스트() {
        Group group = Group.builder()
                .id(1L)
                .conditions(Arrays.asList(
                        new Condition(Key.METHOD_TYPE, Operator.EQUALS, new Value("SEND")),
                        new Condition(Key.RESIDENCE, Operator.NOT_EQUALS, new Value("$region"))
                ))
                .description("description")
                .build();

        GroupEntity groupEntity = GroupEntity.from(group);

        assertThat(groupEntity).isNotNull();
        assertThat(groupEntity.getId()).isEqualTo(group.getId());
        assertThat(groupEntity.getConditions()).hasSize(group.getConditions().size());
        assertThat(groupEntity.getDescription()).hasToString(group.getDescription());
    }

    @Test
    @DisplayName("GroupEntity에서 Group로의 변환 테스트")
    void GroupEntity_to_Group_변환_테스트() {
        GroupEntity groupEntity = GroupEntity.builder()
                .id(1L)
                .conditions(Arrays.asList(
                        new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("SEND")),
                        new QslCondition(QslKey.RESIDENCE, QslOperator.NOT_EQUALS, new QslValue("$region"))
                ))
                .description("description")
                .build();

        Group convertedGroup = groupEntity.convert();

        assertThat(convertedGroup).isNotNull();
        assertThat(convertedGroup.getId()).isEqualTo(groupEntity.getId());
        assertThat(convertedGroup.getConditions()).hasSize(groupEntity.getConditions().size());
        assertThat(convertedGroup.getDescription()).hasToString(groupEntity.getDescription());
    }

    @Test
    @DisplayName("단일 BooleanExpression 정상 반환 테스트")
    void 단일_BooleanExpression_반환() {
        List<QslCondition> qslConditions = new ArrayList<>(Arrays.asList(
                new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("SEND"))
        ));
        String description = "SEND payment";
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .description(description)
                .conditions(qslConditions)
                .build();

        BooleanExpression[] expressions = group.expressionForCondition();

        assertThat(expressions).hasSize(1);
        assertThat(expressions[0].toString()).hasToString("paymentEntity.methodType = SEND");
    }

    @Test
    @DisplayName("다중 BooleanExpression 정상 반환 테스트")
    void 다중_BooleanExpression_반환() {
        List<QslCondition> qslConditions = new ArrayList<>(Arrays.asList(
                new QslCondition(QslKey.METHOD_TYPE, QslOperator.EQUALS, new QslValue("SEND")),
                new QslCondition(QslKey.RESIDENCE, QslOperator.NOT_EQUALS, new QslValue("$region"))
        ));
        String description = "SEND and not region payment";
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .description(description)
                .conditions(qslConditions)
                .build();

        BooleanExpression[] expressions = group.expressionForCondition();

        assertThat(expressions).hasSize(2);
        assertThat(expressions[0].toString()).hasToString("paymentEntity.methodType = SEND");
        assertThat(expressions[1].toString()).hasToString("accountEntity.residence != paymentEntity.region");
    }
}