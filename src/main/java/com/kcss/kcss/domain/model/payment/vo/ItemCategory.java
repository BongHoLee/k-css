package com.kcss.kcss.domain.model.payment.vo;


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
                .filter(itemCategory -> itemCategory.categoryName().equals(categoryName))
                .findFirst()
                .orElseThrow();
    }
}
