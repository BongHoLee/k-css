package com.kcss.kcss.domain.model.group.vo;


import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@EqualsAndHashCode
@Slf4j
public class Value {
    private static final Pattern NOT_VALID_PATTERN = Pattern.compile("[!@#%^&*().?\":{}|<>]");
    @Getter
    private final String value;

    public Value(String value) {
        validation(value);
        this.value = value;
    }

    private void validation(String value) {
        if (value == null || NOT_VALID_PATTERN.matcher(value).find() || !isValidMultiValue(value)) {
            log.error("not support value pattern : {}", value);
            throw new BusinessException("not support value pattern : " + value, DomainErrorCode.NOT_SUPPORT_VALUE_PATTERN);
        }
    }

    private boolean isValidMultiValue(String value) {
        if (value.contains(",")) {
            return value.startsWith("[") && value.endsWith("]");
        }
        return true;
    }

}
