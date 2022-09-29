package com.kcss.kcss.domain.model.group.vo;


import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import java.util.Arrays;

public enum Key {
    REGION("region"),
    RESIDENCE("residence"),
    METHOD_TYPE("methodType"),
    ITEM_CATEGORY("itemCategory"),
    ACCOUNT_ID("accountId"),
    AMOUNT("amount"),
    AGE("age")
    ;

    private final String keyName;

    Key(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return this.keyName;
    }


    public static Key of(String keyName) {
        return Arrays.stream(Key.values())
                .filter(key -> key.getKeyName().equals(keyName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support key : " + keyName, DomainErrorCode.NOT_SUPPORT_KEY));
    }
}
