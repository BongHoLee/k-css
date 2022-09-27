package com.kcss.kcss.domain.model.payment;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.infrastructure.entity.account.AccountEntity;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    @DisplayName("AccountEntity에서 Account 변환 테스트")
    void AccountEntity_to_Account_변환_테스트() {
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id(1L)
                .accountEntity(AccountEntity.builder().id(1L).residence("제주").age(30L).build())
                .itemCategory("패션")
                .amount(30.0)
                .methodType("송금")
                .region("서울")
                .build();

        Payment converted = paymentEntity.convert();

        assertThat(converted.getId()).isEqualTo(paymentEntity.getId());
        assertThat(converted.getAmount().amount()).isEqualTo(paymentEntity.getAmount());
        assertThat(converted.getItemCategory().categoryName()).isEqualTo(paymentEntity.getItemCategory());
        assertThat(converted.getMethodType().methodTypeName()).isEqualTo(paymentEntity.getMethodType());
        assertThat(converted.getRegion().regionName()).isEqualTo(paymentEntity.getRegion());

        assertThat(converted.getAccount().getId()).isEqualTo(paymentEntity.getAccountEntity().getId());
        assertThat(converted.getAccount().getAge().age()).isEqualTo(paymentEntity.getAccountEntity().getAge());
        assertThat(converted.getAccount().getResidence().residenceName())
                .isEqualTo(paymentEntity.getAccountEntity().getResidence());
    }

    @Test
    @DisplayName("Account에서 AccountEntity 변환 테스트")
    void Account_to_AccountEntity_변환_테스트() {


        Payment payment = Payment.builder()
                .id(1L)
                .account(Account.builder().id(1L).age(Age.of(30L)).residence(Residence.BUSAN).build())
                .amount(Amount.of(30.0))
                .itemCategory(ItemCategory.BEAUTY)
                .methodType(MethodType.CARD)
                .region(Region.BUSAN).build();

        PaymentEntity paymentEntity = PaymentEntity.from(payment);

        assertThat(payment.getId()).isEqualTo(paymentEntity.getId());
        assertThat(payment.getAmount().amount()).isEqualTo(paymentEntity.getAmount());
        assertThat(payment.getItemCategory().categoryName()).isEqualTo(paymentEntity.getItemCategory());
        assertThat(payment.getMethodType().methodTypeName()).isEqualTo(paymentEntity.getMethodType());
        assertThat(payment.getRegion().regionName()).isEqualTo(paymentEntity.getRegion());

        assertThat(payment.getAccount().getId()).isEqualTo(paymentEntity.getAccountEntity().getId());
        assertThat(payment.getAccount().getAge().age()).isEqualTo(paymentEntity.getAccountEntity().getAge());
        assertThat(payment.getAccount().getResidence().residenceName())
                .isEqualTo(paymentEntity.getAccountEntity().getResidence());
    }

}