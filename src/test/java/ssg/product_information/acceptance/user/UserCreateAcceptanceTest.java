package ssg.product_information.acceptance.user;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
import ssg.product_information.exception.dto.ExceptionResponse;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("사용자 생성 인수 테스트")
public class UserCreateAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("새로운 사용자 정보를 입력하는 api 테스트")
    void createUser() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반", "정상");

        // when
        ExtractableResponse<Response> response = 새로운_사용자_정보_등록_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자의 이름은 비어 있을 수 없다.")
    void emptyName(String name) {
        // given
        UserCreateRequest request = new UserCreateRequest(name, "일반", "정상");

        // when
        ExtractableResponse<Response> response = 새로운_사용자_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자의 타입은 비어 있을 수 없다.")
    void emptyType(String type) {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", type, "정상");

        // when
        ExtractableResponse<Response> response = 새로운_사용자_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("타입은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자의 상태는 비어 있을 수 없다.")
    void emptyStat(String stat) {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반", stat);

        // when
        ExtractableResponse<Response> response = 새로운_사용자_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("상태는 비어있을 수 없습니다.");
    }

    private ExtractableResponse<Response> 새로운_사용자_정보_등록_요청(UserCreateRequest request) {
        return RestAssured.given(super.spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/users")
                          .then()
                          .log().all()
                          .extract();
    }
}
