package ssg.product_information.unit.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.exception.user.NoSuchUserTypeException;
import ssg.product_information.user.domain.UserType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("유저 타입 단위 테스트")
public class UserTypeTest {

    @Test
    @DisplayName("문자열에 맞게 UserType 인스턴스를 반환한다")
    void getType() {
        // given
        String request = "일반";

        // when
        UserType type = UserType.getType(request);

        // then
        assertThat(type).isEqualTo(UserType.GENERAL);
    }

    @Test
    @DisplayName("없는 문자열인 경우 예외를 발생시킨다.")
    void noSuchUserType() {
        // given
        String request = "vip";

        // when & then
        assertThatThrownBy(() -> UserType.getType(request))
                .isInstanceOf(NoSuchUserTypeException.class);
    }
}
