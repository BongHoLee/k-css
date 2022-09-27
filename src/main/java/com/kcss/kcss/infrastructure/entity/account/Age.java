package com.kcss.kcss.infrastructure.entity.account;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {
    private int age;
    public Age (int age) {
        if (age < 1) {
            throw new IllegalArgumentException("AGE MUST LARGER THAN 0");
        }
        this.age = age;
    }
}
