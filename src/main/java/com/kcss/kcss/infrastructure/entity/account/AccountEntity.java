package com.kcss.kcss.infrastructure.entity.account;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import javax.persistence.Column;
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

    @Column
    private String residence;

    @Column
    private Long age;

    @Builder
    public AccountEntity(Long id, String residence, Long age) {
        this.id = id;
        this.residence = residence;
        this.age = age;
    }

    public static AccountEntity from(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .age(account.getAge().age())
                .residence(account.getResidence().residenceName())
                .build();
    }

    public Account convert() {
        return Account.builder()
                .id(id)
                .age(Age.of(age))
                .residence(Residence.of(residence))
                .build();
    }
}
