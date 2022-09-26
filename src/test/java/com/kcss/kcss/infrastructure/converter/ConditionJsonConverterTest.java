package com.kcss.kcss.infrastructure.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kcss.kcss.infrastructure.common.converter.ConditionJsonConverter;
import com.kcss.kcss.infrastructure.entity.group.condition.Condition;
import com.kcss.kcss.infrastructure.entity.group.condition.Key;
import com.kcss.kcss.infrastructure.entity.group.condition.Operator;
import com.kcss.kcss.infrastructure.entity.group.condition.Value;
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
        List<Condition> conditions =
                new ArrayList<>(Arrays.asList(
                        new Condition(Key.REGION, Operator.IN, new Value("[서울, 경기]")),
                        new Condition(Key.ITEM_CATEGORY, Operator.EQUALS, new Value("패션")),
                        new Condition(Key.AGE, Operator.BETWEEN, new Value("[30, 60]"))
                ));

        String jsonStr = converter.convertToDatabaseColumn(conditions);
        List<Condition> converted = converter.convertToEntityAttribute(jsonStr);


        assertThat(jsonStr)
                .contains("key")
                .contains("[서울, 경기]")
                .contains("[30, 60]");

        assertThat(converted).containsAll(conditions);
    }
}