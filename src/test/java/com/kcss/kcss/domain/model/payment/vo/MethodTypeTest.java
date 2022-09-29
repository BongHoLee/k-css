package com.kcss.kcss.domain.model.payment.vo;


import com.kcss.kcss.global.error.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MethodTypeTest {

    @Test
    @DisplayName("MethodType 한글 명 -> 영문 변환 테스트")
    void 한글_영문_변환_테스트() {
        String send = "송금";
        String card = "카드";
        MethodType sendType = MethodType.of(send);
        MethodType cardType = MethodType.of(card);
        Assertions.assertThat(sendType).isInstanceOf(MethodType.SEND.getClass());
        Assertions.assertThat(cardType).isInstanceOf(MethodType.CARD.getClass());
    }

    @Test
    @DisplayName("MethodType 한글 명 -> 영문 변환 예외 테스트")
    void 한글_영문_변환_예외_테스트() {
        String  ex = "예외";
        Assertions.assertThatThrownBy(() -> MethodType.of(ex)).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("MethodType 영문 -> 한글 변환 테스트")
    void 영문_한글_변환_테스트() {
        String card = "CARD";
        Assertions.assertThat(MethodType.of(card)).isInstanceOf(MethodType.CARD.getClass());
    }

    @Test
    @DisplayName("MethodType 영문 -> 한글 변환 예외 테스트")
    void 영문_한글_변환_예외_테스트() {
        String ex = "한글";
        Assertions.assertThatThrownBy(() -> MethodType.of(ex)).isInstanceOf(BusinessException.class);
    }
}