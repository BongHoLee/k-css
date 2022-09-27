package com.kcss.kcss.domain.model.payment.vo;


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
}
