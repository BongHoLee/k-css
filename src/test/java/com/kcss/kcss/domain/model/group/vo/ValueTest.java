package com.kcss.kcss.domain.model.group.vo;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kcss.kcss.global.error.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValueTest {

    private static final String EXCEPTION_MESSAGE =  "not support value pattern";
    @Test
    @DisplayName("[] 패턴 value 생성 성공 테스트")
    void 리스트_value_생성_패턴_테스트() {

        assertDoesNotThrow(() -> {new Value("[a1, a2, a3]");});
        assertDoesNotThrow(() -> {new Value("[b1, b2, $region]");});

        assertThatThrownBy(() -> {new Value("a1, a2, a3");}).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("$, [] 외 특수문자 BusinessException 발생 테스트")
    void 허용된_문자외_특수문자_예외테스트() {
        assertDoesNotThrow(() -> {new Value("[a1, a2, a3]");});
        assertDoesNotThrow(() -> {new Value("$a");});

        assertThatThrownBy(() -> {new Value("#a") ;}).isInstanceOf(BusinessException.class);
        assertThatThrownBy(() -> {new Value("*a") ;}).isInstanceOf(BusinessException.class);
        assertThatThrownBy(() -> {new Value(".a") ;}).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("NULL 예외 테스트")
    void NULL_예외발생_테스트() {
        assertThatThrownBy(() -> {new Value(null) ;}).isInstanceOf(BusinessException.class);
    }
}