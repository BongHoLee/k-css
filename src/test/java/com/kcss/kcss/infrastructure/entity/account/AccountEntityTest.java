package com.kcss.kcss.infrastructure.entity.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountEntityTest {

    @Test
    @DisplayName("AccountEntity에서 Account 정상 변환 테스트")
    void AccountEntity_to_Account_변환_테스트() {

        AccountEntity accountEntity = AccountEntity.builder()
                .id(1L)
                .age(30L)
                .residence("제주")
                .build();

        Account converted = accountEntity.convert();

        assertThat(converted.getId()).isEqualTo(accountEntity.getId());
        assertThat(converted.getAge().age()).isEqualTo(accountEntity.getAge());
        assertThat(converted.getResidence().residenceName()).isEqualTo(accountEntity.getResidence());
    }

    @Test
    @DisplayName("Account에서 AccountEntity 정상 변환 테스트")
    void Account_to_AccountEntity_변환_테스트() {

        Account account = Account.builder()
                .id(1L)
                .age(Age.of(30L))
                .residence(Residence.BUSAN)
                .build();

        AccountEntity converted = AccountEntity.from(account);

        assertThat(converted.getId()).isEqualTo(account.getId());
        assertThat(converted.getResidence()).isEqualTo(account.getResidence().residenceName());
        assertThat(converted.getAge()).isEqualTo(account.getAge().age());
    }
}