package com.kcss.kcss.infrastructure.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kcss.kcss.infrastructure.common.converter.ConditionJsonConverter;
import com.kcss.kcss.infrastructure.entity.group.vo.QslCondition;
import com.kcss.kcss.infrastructure.entity.group.vo.QslKey;
import com.kcss.kcss.infrastructure.entity.group.vo.QslOperator;
import com.kcss.kcss.infrastructure.entity.group.vo.QslValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConditionJsonConverterTest {

    @Test
    @DisplayName("Condition JSON 간 상호 변환 테스트")
    void Condition_JSON_변환_테스트() throws JsonProcessingException {
        ConditionJsonConverter converter = new ConditionJsonConverter();
        List<QslCondition> qslConditions =
                new ArrayList<>(Arrays.asList(
                        new QslCondition(QslKey.REGION, QslOperator.IN, new QslValue("[서울, 경기]")),
                        new QslCondition(QslKey.ITEM_CATEGORY, QslOperator.EQUALS, new QslValue("패션")),
                        new QslCondition(QslKey.AGE, QslOperator.BETWEEN, new QslValue("[30, 60]"))
                ));

        String jsonStr = converter.convertToDatabaseColumn(qslConditions);
        List<QslCondition> converted = converter.convertToEntityAttribute(jsonStr);


        assertThat(jsonStr)
                .contains("key")
                .contains("[서울, 경기]")
                .contains("[30, 60]");

        assertThat(converted).containsAll(qslConditions);
    }
}