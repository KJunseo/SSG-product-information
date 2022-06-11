package ssg.product_information.unit.item;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.item.ItemDisplayPeriodException;
import ssg.product_information.item.domain.Item;
import ssg.product_information.item.domain.ItemType;

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
}
