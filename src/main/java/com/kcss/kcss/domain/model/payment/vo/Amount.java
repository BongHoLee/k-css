package com.kcss.kcss.domain.model.payment.vo;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


@EqualsAndHashCode
@Slf4j
public class Amount {
    private Double amount;
    private Amount() {}
    private Amount(Double amount) {
        validation(amount);
        this.amount = amount;
    }

    public Amount add(Amount otherAmount) {
        return of(this.amount + otherAmount.amount);
    }

    public Amount subtract(Amount otherAmount) {
        return of(this.amount - otherAmount.amount);
    }
    private void validation(Double amount) {
        if (amount < 0) {
            log.error("amount cannot be less than 0");
            throw new IllegalStateException("amount cannot be less than 0");
        }
    }
    public Double amount() {
        return this.amount;
    }
    public static Amount of(Double amount) {
        return new Amount(amount);
    }
}
