package ssg.product_information.unit.promotion;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.promotion.PromotionItemDisplayPeriodException;
import ssg.product_information.exception.promotion.PromotionItemNumberException;
import ssg.product_information.exception.promotion.PromotionPeriodException;
import ssg.product_information.exception.promotion.ViolateDiscountPolicyException;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.promotion.domain.Promotion;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("프로모션 단위 테스트")
class PromotionTest {

    @Test
    @DisplayName("할인 가격이 존재하고 할인율이 존재하지 않는다면 생성 가능하다.")
    void discountPrice() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        int discountAmount = 1000;

        // when & then
        assertThatNoException()
                .isThrownBy(() -> new Promotion("쓱데이", discountAmount, start, end));
    }

    @Test
    @DisplayName("할인율이 존재하고 할인가격이 존재하지 않는다면 생성 가능하다.")
    void discountRate() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        double discountRate = 0.05;

        // when & then
        assertThatNoException()
                .isThrownBy(() -> new Promotion("쓱데이", discountRate, start, end));
    }

    @Test
    @DisplayName("할인 가격과 할인율이 둘 다 존재하면 예외가 발생한다.")
    void violateDiscountPolicy() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        int discountAmount = 1000;
        double discountRate = 0.05;

        // when & then
        assertThatThrownBy(() -> new Promotion("쓱데이", discountAmount, discountRate, start, end))
                .isInstanceOf(ViolateDiscountPolicyException.class);
    }

    @Test
    @DisplayName("할인 가격과 할인율이 둘 다 존재하지 않으면 예외가 발생한다.")
    void violateDiscountPolicyNotExist() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);

        // when & then
        assertThatThrownBy(() -> new Promotion("쓱데이", null, null, start, end))
                .isInstanceOf(ViolateDiscountPolicyException.class);
    }

    @Test
    @DisplayName("프로모션 시작일이 프로모션 마감일 이전이라면 예외가 발생한다.")
    void wrongPeriod() {
        // given
        LocalDate start = LocalDate.of(2022, 7, 15);
        LocalDate end = LocalDate.of(2022, 6, 15);
        int discountAmount = 1000;

        // when & then
        assertThatThrownBy(() -> new Promotion("쓱데이", discountAmount, start, end))
                .isInstanceOf(PromotionPeriodException.class);
    }

    @Test
    @DisplayName("프로모션에 0개의 상품이 들어올 수 없다.")
    void wrongItemNumber() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        int discountAmount = 1000;
        Promotion promotion = new Promotion("쓱데이", discountAmount, start, end);

        // when & then
        assertThatThrownBy(() -> promotion.addItems(Collections.emptyList()))
                .isInstanceOf(PromotionItemNumberException.class);
    }

    @Test
    @DisplayName("프로모션 기간 동안 하루라도 전시되지 않는 상품이 포함되어 있는 경우")
    void wrongItem() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        int discountAmount = 1000;
        Promotion promotion = new Promotion("쓱데이", discountAmount, start, end);

        Item item = new Item(
                "새콤달콤",
                ItemType.GENERAL_MEMBERSHIP,
                500,
                LocalDate.of(2022, 6, 20),
                LocalDate.of(2022, 8, 15)
        );

        // when & then
        assertThatThrownBy(() -> promotion.addItems(List.of(item)))
                .isInstanceOf(PromotionItemDisplayPeriodException.class);
    }
}
