package com.kcss.kcss.domain.model.group.vo;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConditionTest {
    private final static String EXCEPTION_MESSAGE = "Condition should be stable";

    @Test
    @DisplayName("Condition 필드 누락 시 BusinessException 발생 테스트")
    void Condition_필드_일부_누락시_예외발생_테스트() {
        assertThatThrownBy(() -> {Condition.builder().build();}).isInstanceOf(BusinessException.class)
                .hasMessageContaining(DomainErrorCode.NOT_STABLE_CONDITION.getMessage());
        assertThatThrownBy(() -> {Condition.builder().key(Key.AGE).build();}).isInstanceOf(BusinessException.class)
                .hasMessageContaining(DomainErrorCode.NOT_STABLE_CONDITION.getMessage());
        assertThatThrownBy(() -> {Condition.builder().operator(Operator.BETWEEN).build();}).isInstanceOf(BusinessException.class)
                .hasMessageContaining(DomainErrorCode.NOT_STABLE_CONDITION.getMessage());
        assertThatThrownBy(() -> {Condition.builder().operator(Operator.BETWEEN).key(Key.AMOUNT).build();}).isInstanceOf(BusinessException.class)
                .hasMessageContaining(DomainErrorCode.NOT_STABLE_CONDITION.getMessage());
    }

    @Test
    @DisplayName("Condition 필드 전체 주입 시 정상 생성 테스트")
    void Condition_필드_전체_주입시_정상_생성() {
        assertDoesNotThrow(() -> {
            Condition.builder()
                    .key(Key.AMOUNT)
                    .value(new Value("$region"))
                    .operator(Operator.IN)
                    .build();
        });

    }
}