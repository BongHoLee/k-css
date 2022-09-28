package com.kcss.kcss.domain.model.account;

import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.model.payment.Payment;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@EqualsAndHashCode
public class Account {
    private final Long id;
    private final Age age;
    private final Residence residence;
    private final List<Payment> payments;

    @Builder
    public Account(Long id, Age age, Residence residence, List<Payment> payments) {
        this.id = id;
        this.residence = residence;
        this.age = age;
        this.payments = payments == null ? Collections.emptyList() : payments;
    }

}
