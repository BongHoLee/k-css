package com.kcss.kcss.infrastructure.repository.account;


import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
@Transactional
@SpringBootTest
class AccountRepositoryImplTest {

    @Autowired
    private AccountRepositoryImpl accountRepository;

    @Test
    @DisplayName("Account 영속화 테스트")
    void Account_save__테스트() {
        Account account = Account.builder()
                .age(Age.of(39L))
                .residence(Residence.DAEJEON)
                .build();

        Assertions.assertDoesNotThrow(() -> accountRepository.save(account));
    }

    @Test
    @DisplayName("Account 반환 테스트")
    void Account_findById_테스트() {
        Optional<Account> found = accountRepository.findById(2L);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(2L);
        assertThat(found.get().getResidence()).isEqualTo(Residence.SEJONG);
        assertThat(found.get().getAge()).isEqualTo(Age.of(73L));
        assertThat(found.get().getPayments()).hasSize(3);
    }
}