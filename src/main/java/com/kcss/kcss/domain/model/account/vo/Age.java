package com.kcss.kcss.domain.model.account.vo;

import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import lombok.EqualsAndHashCode;
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
        if (age == null || age < 0) {
            log.error("not valid age : {} ", age);
            throw new BusinessException(DomainErrorCode.NOT_VALID_AGE);
        }
    }

    public Long age() {
        return age;
    }

    public static Age of(Long age) {
        return new Age(age);
    }
}
