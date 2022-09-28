package com.kcss.kcss.infrastructure.repository.account;


import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import java.util.Optional;
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
    @DisplayName("Account 영속화 및 반환 테스트")
    void Account_save_and_findById_테스트() {
        Account account = Account.builder()
                .age(Age.of(39L))
                .residence(Residence.DAEJEON)
                .build();

        Account saved = accountRepository.save(account);
        Optional<Account> found = accountRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotZero();
        assertThat(found.get().getResidence()).isEqualTo(account.getResidence());
        assertThat(found.get().getAge()).isEqualTo(account.getAge());
    }
}