package ssg.product_information.acceptance.item;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("상품 생성 인수 테스트")
public class ItemCreateAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("새로운 상품 정보 생성")
    void createItem() {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    private ExtractableResponse<Response> 새로운_상품_정보_등록_요청(ItemCreateRequest request) {
        return RestAssured.given(super.spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/items")
                          .then()
                          .log().all()
                          .extract();
    }

}
