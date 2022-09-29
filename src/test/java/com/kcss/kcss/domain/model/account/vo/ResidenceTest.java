package com.kcss.kcss.domain.model.account.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class ResidenceTest {

    @Test
    @DisplayName("문자열 Residence 정상 변환 테스트")
    void 정상_변환_테스트() {
        assertThat(Residence.of("서울")).isInstanceOf(Residence.SEOUL.getClass());
        assertThat(Residence.of("대전")).isInstanceOf(Residence.DAEJEON.getClass());
        assertThat(Residence.of("대구")).isInstanceOf(Residence.DAEGU.getClass());
        assertThat(Residence.of("부산")).isInstanceOf(Residence.BUSAN.getClass());
        assertThat(Residence.of("세종")).isInstanceOf(Residence.SEJONG.getClass());
    }

    @Test
    @DisplayName("지원하지 않는 문자열 Residence 변환 시 BusinessException 발생 테스트")
    void 비정상_예외발생_테스트() {
         String exceptionMessage = "not support residence";
        assertThatThrownBy(() -> Residence.of("뉴욕")).hasMessageContaining(exceptionMessage);
        assertThatThrownBy(() -> Residence.of("동경")).hasMessageContaining(exceptionMessage);
        assertThatThrownBy(() -> Residence.of("북경")).hasMessageContaining(exceptionMessage);

    }

}