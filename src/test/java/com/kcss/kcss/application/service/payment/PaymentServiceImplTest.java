package com.kcss.kcss.application.service.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.application.service.payment.PaymentServiceImpl;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.global.error.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Sql("/data/schema.sql")
@Sql("/data/data.sql")
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PaymentServiceImplTest {

    @Autowired
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("결제 정상 처리 테스트")
    void 결제_발행_정상_처리_테스트() {
        Payment payment = Payment.builder()
                .id(1L)
                .amount(Amount.of(30000.0))
                .itemCategory(ItemCategory.BEAUTY)
                .methodType(MethodType.CARD)
                .region(Region.BUSAN)
                .account(Account.builder().id(1L).build())
                .build();

        assertDoesNotThrow(() -> paymentService.pay(payment));
    }

    @Test
    @DisplayName("결제내역 반환 정상 처리 테스트")
    void 결제_내역_반환_정상_처리_테스트() {
        Payment payment = paymentService.paymentOf(2L);
        assertThat(payment.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("미존재 결제 내역 조회 시 NO CONTENT 메시지 테스트")
    void 미존재_결제내역_조회_테스트() {
        assertThatThrownBy(() -> paymentService.paymentOf(1000L)).isInstanceOf(BusinessException.class)
                .hasMessageContaining(ApplicationErrorCode.NO_CONTENT.getMessage());
    }
}