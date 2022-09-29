package com.kcss.kcss.domain.model.payment.vo;

import com.kcss.kcss.global.error.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemCategoryTest {

    @Test
    @DisplayName("ItemCateory 한글 명 -> 영문 변환 테스트")
    void 한글_영문_변환_테스트() {
        String food = "식품";
        String beauty = "뷰티";
        ItemCategory foodType = ItemCategory.of(food);
        ItemCategory beautyType = ItemCategory.of(beauty);
        Assertions.assertThat(foodType).isInstanceOf(ItemCategory.FOOD.getClass());
        Assertions.assertThat(beautyType).isInstanceOf(ItemCategory.BEAUTY.getClass());
    }

    @Test
    @DisplayName("ItemCateory 한글 명 -> 영문 변환 예외 테스트")
    void 한글_영문_변환_예외_테스트() {
        String  ex = "예외";
        Assertions.assertThatThrownBy(() -> ItemCategory.of(ex)).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("ItemCateory 영문 -> 한글 변환 테스트")
    void 영문_한글_변환_테스트() {
        String food = "FOOD";
        String beauty = "BEAUTY";
        Assertions.assertThat(ItemCategory.of(food)).isInstanceOf(ItemCategory.FOOD.getClass());
        Assertions.assertThat(ItemCategory.of(beauty)).isInstanceOf(ItemCategory.BEAUTY.getClass());
    }
}