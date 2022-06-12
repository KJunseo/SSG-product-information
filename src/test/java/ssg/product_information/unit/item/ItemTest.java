package ssg.product_information.unit.item;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.item.ItemDisplayPeriodException;
import ssg.product_information.exception.promotion.PromotionItemDisplayPeriodException;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemType;
import ssg.product_information.item.domain.discount.DiscountAmountPolicy;
import ssg.product_information.item.domain.discount.DiscountPolicy;
import ssg.product_information.item.domain.discount.DiscountRatePolicy;
import ssg.product_information.promotion.domain.Promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 단위 테스트")
class ItemTest {

    @Test
    @DisplayName("전시 마감일이 전시 시작일 이전이라면 예외가 발생한다.")
    void wrongPeriod() {
        // given
        LocalDate start = LocalDate.of(2022, 7, 15);
        LocalDate end = LocalDate.of(2022, 6, 15);

        // when & then
        assertThatThrownBy(() -> new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, start, end))
                .isInstanceOf(ItemDisplayPeriodException.class);
    }

    @Test
    @DisplayName("상품 전시 기간이 프로모션 기간보다 길어야 한다.")
    void checkDisplayPeriod() {
        // given
        LocalDate start = LocalDate.of(2022, 6, 15);
        LocalDate end = LocalDate.of(2022, 7, 15);
        Item item = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, start, end);

        // when & then
        assertThatThrownBy(() -> item.checkDisplayPeriod(start.minusDays(1), end))
                .isInstanceOf(PromotionItemDisplayPeriodException.class);
    }

    @Test
    @DisplayName("현재 시점이 상품 기간에 포함되는지 판별")
    void isDisplay() {
        // given
        LocalDate now = LocalDate.now();
        Item item1 = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, now.minusDays(10), now.plusDays(10));
        Item item2 = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 500, now.plusDays(5), now.plusDays(10));

        // when
        boolean display = item1.isDisplay();
        boolean notDisplay = item2.isDisplay();

        // then
        assertThat(display).isTrue();
        assertThat(notDisplay).isFalse();
    }

    @Test
    @DisplayName("할인 적용 - 정액")
    void discountAmount() {
        // given
        LocalDate now = LocalDate.now();
        Item item = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 1500, now.minusDays(10), now.plusDays(10));
        DiscountPolicy policy = new DiscountAmountPolicy();
        Promotion promotion = new Promotion("쓱데이", 1000, now.minusDays(10), now.plusDays(10));

        // when
        item.discount(policy, promotion);

        // then
        assertThat(item.getDiscountPrice()).isEqualTo(500);
    }

    @Test
    @DisplayName("할인 적용 - 정률")
    void discountRate() {
        // given
        LocalDate now = LocalDate.now();
        Item item = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 2000, now.minusDays(10), now.plusDays(10));
        DiscountPolicy policy = new DiscountRatePolicy();
        Promotion promotion = new Promotion("쓱데이", 0.5, now.minusDays(10), now.plusDays(10));

        // when
        item.discount(policy, promotion);

        // then
        assertThat(item.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("할인된 가격이 0이하라면 적용되지 않는다.")
    void underZeroPrice() {
        // given
        LocalDate now = LocalDate.now();
        Item item = new Item("새콤달콤", ItemType.GENERAL_MEMBERSHIP, 2000, now.minusDays(10), now.plusDays(10));
        DiscountPolicy policy = new DiscountAmountPolicy();
        Promotion promotion = new Promotion("쓱데이", 2000, now.minusDays(10), now.plusDays(10));

        // when
        item.discount(policy, promotion);

        // then
        assertThat(item.getDiscountPrice()).isNull();
    }
}
