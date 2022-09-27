package com.kcss.kcss.domain.model.account;

import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@EqualsAndHashCode
public class Account {
    private Long id;
    private final Age age;
    private final Residence residence;

    public Account(Age age, Residence residence) {
        this.residence = residence;
        this.age = age;
    }

}
