package com.kcss.kcss.domain.model.account.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode
public class Age {
    private final Long age;

    private Age(Long age) {
        validation(age);
        this.age = age;
    }

    private void validation(Long age) {
        if (age == null || age < 1) {
            log.error("not valid age : {} ", age);
            throw new IllegalArgumentException("not valid age" + age);
        }
    }

    public Long age() {
        return age;
    }

    public static Age of(Long age) {
        return new Age(age);
    }
}
