package com.kcss.kcss.domain.model.payment;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@EqualsAndHashCode
@Slf4j
public class Payment {
    private final Long id;
    private final Account account;
    private final Amount amount;
    private final MethodType methodType;
    private final ItemCategory itemCategory;
    private final Region region;

    @Builder
    public Payment(
            Long id, Account account, Amount amount, MethodType methodType, ItemCategory itemCategory, Region region) {
        validation(account, amount, methodType, itemCategory, region);
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.methodType = methodType;
        this.itemCategory = itemCategory;
        this.region = region;
    }

    private void validation(Account account, Amount amount, MethodType methodType, ItemCategory itemCategory, Region region) {
        if (account == null || amount == null || methodType == null || itemCategory == null || region == null) {
            log.error("Payment should be stable");
            throw new IllegalArgumentException("Payment should be stable");
        }
    }
}
