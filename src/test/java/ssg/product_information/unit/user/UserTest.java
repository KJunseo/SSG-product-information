package ssg.product_information.unit.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.user.AlreadyWithdrawalException;
import ssg.product_information.user.domain.User;
import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("사용자 단위 테스트")
public class UserTest {

    @Test
    @DisplayName("탈퇴 요청시 상태값이 정상에서 탈퇴로 바뀐다.")
    void withdrawal() {
        // given
        User user = new User("김준서", UserType.GENERAL);

        // when
        user.withdrawal();

        // then
        assertThat(user.getUserStat()).isEqualTo(UserStat.WITHDRAWAL);
    }

    @Test
    @DisplayName("이미 탈퇴한 사용자는 탈퇴할 수 없다.")
    void alreadyWithdrawal() {
        // given
        User user = new User("김준서", UserType.GENERAL, UserStat.WITHDRAWAL);

        // when & then
        assertThatThrownBy(user::withdrawal)
                .isInstanceOf(AlreadyWithdrawalException.class);
    }
}
