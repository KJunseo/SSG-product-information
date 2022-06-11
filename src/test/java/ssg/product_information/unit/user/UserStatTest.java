package ssg.product_information.unit.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.user.NoSuchUserStatException;
import ssg.product_information.user.domain.UserStat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("유저 상태 단위 테스트")
public class UserStatTest {

    @Test
    @DisplayName("문자열에 맞게 UserStat 인스턴스를 반환한다")
    void getStat() {
        // given
        String request = "정상";

        // when
        UserStat stat = UserStat.getStat(request);

        // then
        assertThat(stat).isEqualTo(UserStat.NORMAL);
    }

    @Test
    @DisplayName("없는 문자열인 경우 예외를 발생시킨다.")
    void noSuchUserStat() {
        // given
        String request = "대기";

        // when & then
        assertThatThrownBy(() -> UserStat.getStat(request))
                .isInstanceOf(NoSuchUserStatException.class);
    }
}
