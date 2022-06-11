package ssg.product_information.acceptance.user;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
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
