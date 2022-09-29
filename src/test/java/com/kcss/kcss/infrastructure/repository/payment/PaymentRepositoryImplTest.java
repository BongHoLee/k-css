package com.kcss.kcss.infrastructure.repository.payment;


import static org.assertj.core.api.Assertions.assertThat;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.infrastructure.repository.account.AccountRepositoryImpl;
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
class PaymentRepositoryImplTest {

    @Autowired
    private PaymentRepositoryImpl paymentRepository;

    @Autowired
    private AccountRepositoryImpl accountRepository;


    @Test
    @DisplayName("Payment 영속화 테스트")
    void Payment_save_테스트() {
        Account account = Account.builder().id(1L).age(Age.of(30L)).residence(Residence.BUSAN).build();
        Account savedAccount = accountRepository.save(account);

        Payment payment = Payment.builder()
                .id(1L)
                .account(savedAccount)
                .amount(Amount.of(30.0))
                .itemCategory(ItemCategory.BOOK)
                .methodType(MethodType.CARD)
                .region(Region.CHUNGNAM)
                .build();

        paymentRepository.save(payment);
    }

    @Test
    @DisplayName("Payment 반환 테스트")
    void Payment_save_and_findById_테스트() {
        Optional<Payment> found = paymentRepository.findById(2L);
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotZero();
    }
}