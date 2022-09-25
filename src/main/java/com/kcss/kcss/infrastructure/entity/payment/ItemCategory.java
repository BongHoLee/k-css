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
@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategory {
    private static final Set<String> ITEM_CATEGORIES = new HashSet<>(Arrays.asList("식품", "뷰티", "스포츠", "도서", "패션"));
    private String itemCategory;

    public ItemCategory(String itemCategory) {
        validation(itemCategory);
        this.itemCategory = itemCategory;
    }

    public void validation(String itemCategory) {
        if (itemCategory == null || itemCategory.isEmpty()) {
            log.error("item category cannot be empty");
            throw new IllegalArgumentException("item category cannot be empty");
        }

        if (!ITEM_CATEGORIES.contains(itemCategory)) {
            log.error("not support item category : {}", itemCategory);
            throw new IllegalArgumentException("not support item category " + itemCategory);
        }
    }
}
