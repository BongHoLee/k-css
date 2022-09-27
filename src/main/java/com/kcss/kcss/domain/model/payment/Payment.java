package com.kcss.kcss.domain.model.payment;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class Payment {
    private Long id;
    private final Account account;
    private final Amount amount;
    private final MethodType methodType;
    private final ItemCategory itemCategory;
    private final Region region;

    public Payment(
            Long id, Account account, Amount amount, MethodType methodType, ItemCategory itemCategory, Region region) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.methodType = methodType;
        this.itemCategory = itemCategory;
        this.region = region;
    }
}
