package com.kcss.kcss.infrastructure.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcss.kcss.infrastructure.entity.group.condition.Condition;
import java.util.Collections;
import java.util.List;
import javax.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConditionJsonConverter implements AttributeConverter<List<Condition>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Condition> conditions) {
        try {
            return mapper.writeValueAsString(conditions);
        } catch (JsonProcessingException e) {
            log.error("fail to parse database. conditions to json");
            return "";
        }
    }

    @Override
    public List<Condition> convertToEntityAttribute(String jsonString) {
        try {
            return mapper.readValue(jsonString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("fail to parse database. conditions to json");
            return Collections.emptyList();
        }
    }
}
