package ssg.product_information.unit.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.item.NoSuchItemTypeException;
import ssg.product_information.item.domain.ItemType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 타입 단위 테스트")
class ItemTypeTest {

    @Test
    @DisplayName("문자열에 맞게 ItemType 인스턴스를 반환한다")
    void getType() {
        // given
        String request = "일반";

        // when
        ItemType type = ItemType.getType(request);

        // then
        assertThat(type).isEqualTo(ItemType.GENERAL_MEMBERSHIP);
    }

    @Test
    @DisplayName("없는 문자열인 경우 예외를 발생시킨다.")
    void noSuchItemType() {
        // given
        String request = "vip회원상품";

        // when & then
        assertThatThrownBy(() -> ItemType.getType(request))
                .isInstanceOf(NoSuchItemTypeException.class);
    }
}
