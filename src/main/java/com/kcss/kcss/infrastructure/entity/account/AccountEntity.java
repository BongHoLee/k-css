package com.kcss.kcss.infrastructure.entity.account;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private Long id;

    @Embedded
    private Residence residence;

    @Embedded
    private Age age;

    @Builder
    public AccountEntity(Long id, Residence residence, Age age) {
        this.id = id;
        this.residence = residence;
        this.age = age;
    }
}
