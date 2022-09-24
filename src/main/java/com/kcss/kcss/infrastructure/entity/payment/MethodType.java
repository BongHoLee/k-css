package com.kcss.kcss.infrastructure.entity.payment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MethodType {
    private static final Set<String> METHOD_TYPES = new HashSet<>(Arrays.asList("카드", "송금"));

    private String methodType;

    public MethodType(String methodType) {
        validation(methodType);
        this.methodType = methodType;
    }

    private void validation(String methodType) {
        if (methodType == null || methodType.isEmpty()) {
            log.error("method type cannot be empty");
            throw new IllegalArgumentException("method type cannot be empty");
        }

        if (!METHOD_TYPES.contains(methodType)) {
            log.error("not support method type : {}", methodType);
            throw new IllegalArgumentException("not support method type " + methodType);
        }
    }
}
