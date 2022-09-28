package com.kcss.kcss.infrastructure.entity.account;

import static java.util.stream.Collectors.toList;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountEntity", cascade = CascadeType.REMOVE)
    private List<PaymentEntity> paymentEntities;

    @Builder
    public AccountEntity(Long id, String residence, Long age, List<PaymentEntity> paymentEntities) {
        this.id = id;
        this.residence = residence;
        this.age = age;
        this.paymentEntities = paymentEntities == null ? Collections.emptyList() : paymentEntities;
    }

    public static AccountEntity from(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .age(account.getAge() == null ? 0L : account.getAge().age())
                .residence(account.getResidence() == null ? "" : account.getResidence().residenceName())
                .paymentEntities(account.getPayments().stream().map(payment -> PaymentEntity.builder()
                        .id(payment.getId())
                        .region(payment.getRegion().regionName())
                        .methodType(payment.getMethodType().methodTypeName())
                        .amount(payment.getAmount().amount())
                        .itemCategory(payment.getItemCategory().categoryName())
                        .build()).collect(toList()))
                .build();
    }

    public Account convert() {
        return Account.builder()
                .id(id)
                .age(Age.of(age))
                .residence(Residence.of(residence))
                .payments(paymentEntities.stream().map(paymentEntity -> Payment.builder()
                        .id(paymentEntity.getId())
                        .itemCategory(ItemCategory.of(paymentEntity.getItemCategory()))
                        .methodType(MethodType.of(paymentEntity.getMethodType()))
                        .amount(Amount.of(paymentEntity.getAmount()))
                        .region(Region.of(paymentEntity.getRegion()))
                        .build()
                ).collect(toList()))
                .build();
    }
}
