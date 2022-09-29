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
import com.kcss.kcss.global.error.BusinessException;
import com.kcss.kcss.infrastructure.entity.error.InfrastructureErrorCode;
import com.kcss.kcss.infrastructure.entity.payment.PaymentEntity;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class AccountEntity {

    @Id
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
        validation(id);
        this.id = id;
        this.residence = residence;
        this.age = age;
        this.paymentEntities = paymentEntities == null ? Collections.emptyList() : paymentEntities;
    }

    private void validation(Long id) {
        if (id == null) {
            log.error("payment id cannot be null");
            throw new BusinessException(InfrastructureErrorCode.NOT_VALID_ID);
        }
    }

    public static AccountEntity from(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .age(account.getAge() == null ? 0L : account.getAge().age())
                .residence(account.getResidence() == null ? "" : account.getResidence().residenceName())
                .paymentEntities(account.getPayments().stream().map(payment -> PaymentEntity.builder()
                        .id(payment.getId())
                        .accountEntity(AccountEntity.builder().id(account.getId()).build())
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
                        .account(Account.builder().id(id).build())
                        .itemCategory(ItemCategory.of(paymentEntity.getItemCategory()))
                        .methodType(MethodType.kor(paymentEntity.getMethodType()))
                        .amount(Amount.of(paymentEntity.getAmount()))
                        .region(Region.of(paymentEntity.getRegion()))
                        .build()
                ).collect(toList()))
                .build();
    }
}
