package com.kcss.kcss.domain.model.group.vo;


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
}
