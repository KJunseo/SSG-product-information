package ssg.product_information.integration.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ssg.product_information.integration.IntegrationTest;
import ssg.product_information.user.application.UserService;
import ssg.product_information.user.domain.User;
import ssg.product_information.user.domain.UserRepository;
import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("사용자 통합 테스트")
public class UserIntegrationTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자 삭제 테스트")
    void delete() {
        // given
        User savedUser = userRepository.save(new User("김준서", UserType.GENERAL));
        UserStat originalStat = savedUser.getUserStat();

        // when
        userService.withdrawal(savedUser.getId());
        userRepository.flush();
        Optional<User> user = userRepository.findById(savedUser.getId());

        // then
        assertThat(user).isNotEmpty();
        assertThat(originalStat).isEqualTo(UserStat.NORMAL);
        assertThat(user.get().getUserStat()).isEqualTo(UserStat.WITHDRAWAL);
    }
}
