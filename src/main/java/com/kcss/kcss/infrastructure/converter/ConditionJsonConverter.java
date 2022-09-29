package com.kcss.kcss.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcss.kcss.infrastructure.entity.group.vo.QslCondition;
import java.util.Collections;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
public class ConditionJsonConverter implements AttributeConverter<List<QslCondition>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<QslCondition> qslConditions) {
        try {
            return mapper.writeValueAsString(qslConditions);
        } catch (JsonProcessingException e) {
            log.error("fail to parse database. conditions to json");
            return "";
        }
    }

    @Override
    public List<QslCondition> convertToEntityAttribute(String jsonString) {
        try {
            return mapper.readValue(jsonString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("fail to parse database. conditions to json");
            return Collections.emptyList();
        }
    }
}
