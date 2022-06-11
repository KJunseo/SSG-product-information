package ssg.product_information.unit.promotion;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.promotion.ViolateDiscountPolicyException;
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
                .isThrownBy(() -> new Promotion("쓱데이", discountAmount, null, start, end));
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
                .isThrownBy(() -> new Promotion("쓱데이", null, discountRate, start, end));
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
}
