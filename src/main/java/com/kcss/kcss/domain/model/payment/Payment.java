package com.kcss.kcss.domain.model.payment;

import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.global.error.BusinessException;
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
        validation(id);
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.methodType = methodType;
        this.itemCategory = itemCategory;
        this.region = region;
    }

    private void validation(Long id) {
        if (id == null) {
            log.error("payment id cannot be null");
            throw new BusinessException(DomainErrorCode.NOT_VALID_ID);
        }
    }
}
