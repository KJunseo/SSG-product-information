package ssg.product_information.unit.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.user.UserJoinException;
import ssg.product_information.user.domain.User;
import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("사용자 단위 테스트")
class UserTest {

    @Test
    @DisplayName("탈퇴 상태로 생성할 수 없다.")
    void create() {
        // given
        UserStat stat = UserStat.WITHDRAW;

        // when & then
        assertThatThrownBy(() -> new User("김준서", UserType.GENERAL, stat))
                .isInstanceOf(UserJoinException.class);
    }
}
