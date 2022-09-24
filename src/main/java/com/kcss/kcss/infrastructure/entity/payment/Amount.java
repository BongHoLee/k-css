package com.kcss.kcss.infrastructure.entity.payment;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {
    private Double amount;

    private Amount(Double amount) {
        validation(amount);
        this.amount = amount;
    }

    public Amount add(Amount otherAmount) {
        return of(this.amount + otherAmount.amount);
    }

    private void validation(Double amount) {
        if (amount < 0) {
            log.error("amount cannot be less than 0");
            throw new IllegalStateException("amount cannot be less than 0");
        }
    }
    public static Amount of(Double amount) {
        return new Amount(amount);
    }
}
