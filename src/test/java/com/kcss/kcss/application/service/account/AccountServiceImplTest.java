package com.kcss.kcss.application.service.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
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
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("계정 정상 저장 테스트")
    void 계정_저장_정상_처리_테스트() {
        Account account = Account.builder().id(1L).age(Age.of(30L)).residence(Residence.BUSAN).build();
        assertDoesNotThrow(() -> accountService.register(account));
    }

    @Test
    @DisplayName("계정 조회 정상 처리 테스트")
    void 계정_조회_정상_처리_테스트() {
        Account find = accountService.findAccountOf(2L);
        assertThat(find.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("미존재 계정 조회 시 NO CONTENT 메시지 테스트")
    void 미존재_계정_조회_테스트() {
        assertThatThrownBy(() -> accountService.findAccountOf(1000L)).isInstanceOf(BusinessException.class)
                .hasMessageContaining(ApplicationErrorCode.NO_CONTENT.getMessage());
    }
}