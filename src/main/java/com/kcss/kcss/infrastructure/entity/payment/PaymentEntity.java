package com.kcss.kcss.infrastructure.entity.payment;

import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.infrastructure.entity.account.AccountEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accountId")
    private AccountEntity accountEntity;

    @Column
    private Double amount;

    @Column
    private String methodType;

    @Column
    private String itemCategory;

    @Column
    private String region;

    @Builder
    public PaymentEntity(Long id, AccountEntity accountEntity, Double amount, String methodType,
                         String itemCategory, String region) {
        this.id = id;
        this.accountEntity = accountEntity;
        this.amount = amount;
        this.methodType = methodType;
        this.itemCategory = itemCategory;
        this.region = region;
    }

    public static PaymentEntity from(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .accountEntity(AccountEntity.from(payment.getAccount()))
                .amount(payment.getAmount().amount())
                .methodType(payment.getMethodType().methodTypeName())
                .itemCategory(payment.getItemCategory().categoryName())
                .region(payment.getRegion().regionName())
                .build();
    }

    public Payment convert() {
        return Payment.builder()
                .id(id)
                .account(accountEntity.convert())
                .amount(Amount.of(amount))
                .methodType(MethodType.of(methodType))
                .itemCategory(ItemCategory.of(itemCategory))
                .region(Region.of(region))
                .build();
    }
}
