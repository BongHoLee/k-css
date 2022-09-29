package com.kcss.kcss.domain.model.payment.vo;


import com.kcss.kcss.domain.error.DomainErrorCode;
import com.kcss.kcss.global.error.BusinessException;
import java.util.Arrays;

public enum ItemCategory {
    FOOD("식품"),
    BEAUTY("뷰티"),
    SPORTS("스포츠"),
    BOOK("도서"),
    FASHION("패션")
    ;

    private final String categoryName;
    ItemCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String categoryName() {
        return categoryName;
    }

    public static ItemCategory of(String categoryName) {
        return Arrays.stream(ItemCategory.values())
                .filter(itemCategory -> itemCategory.categoryName().equals(categoryName) || itemCategory.name().equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new BusinessException("not support category : " + categoryName, DomainErrorCode.NOT_SUPPORT_CATEGORY));
    }
}
