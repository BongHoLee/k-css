package com.kcss.kcss.domain.model.account;

import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
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

    @Builder
    public Account(Long id, Age age, Residence residence) {
        this.id = id;
        this.residence = residence;
        this.age = age;
    }

}
