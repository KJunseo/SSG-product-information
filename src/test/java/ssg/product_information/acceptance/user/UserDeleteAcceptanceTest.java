package ssg.product_information.acceptance.user;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
import ssg.product_information.exception.dto.ExceptionResponse;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ssg.product_information.acceptance.user.UserCreateAcceptanceTest.userCreate;

@DisplayName("사용자 탈퇴 인수 테스트")
public class UserDeleteAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("사용자 탈퇴 요청시 유저의 상태를 정상에서 탈퇴로 바꾼다.")
    void withdrawal() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반");
        Long id = userCreate(request);

        // when
        ExtractableResponse<Response> response = 사용자_탈퇴_요청(id);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("존재하지 않는 사용자는 탈퇴할 수 없다.")
    void notExist() {
        // given
        Long id = 100L;

        // when
        ExtractableResponse<Response> response = 사용자_탈퇴_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("이미 탈퇴한 유저는 탈퇴할 수 없다.")
    void alreadyWithdrawal() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반");
        Long id = userCreate(request);

        // when
        사용자_탈퇴_요청(id);
        ExtractableResponse<Response> response = 사용자_탈퇴_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 유저입니다.");
    }

    private ExtractableResponse<Response> 사용자_탈퇴_요청(Long id) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .delete("/users/" + id)
                          .then()
                          .log().all()
                          .extract();
    }
}
