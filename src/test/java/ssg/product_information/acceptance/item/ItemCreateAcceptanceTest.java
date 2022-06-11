package ssg.product_information.acceptance.item;

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

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품의 이름은 비어있을 수 없다.")
    void emptyName(String name) {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest(name, "일반", 500, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품의 타입은 비어있을 수 없다.")
    void emptyType(String type) {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", type, 500, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("타입은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("상품의 가격은 비어있을 수 없다.")
    void emptyPrice() {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", null, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("가격은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품의 전시 시작일은 비어있을 수 없다.")
    void emptyStart(String start) {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", 500, start, "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("시작일은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품의 전시 마감일은 비어있을 수 없다.")
    void emptyEnd(String end) {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", end);

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("마감일은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 상품타입인 경우 예외가 발생한다.")
    void noSuchItemType() {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "vip 멤버십", 500, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 상품 타입입니다.");
    }

    @Test
    @DisplayName("가격이 음수인 경우")
    void negativePrice() {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", -1, "2022-06-10", "2022-08-31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("가격은 음수가 될 수 없습니다.");
    }

    @Test
    @DisplayName("시작일이나 마감일이 yyyy-mm-dd 형태가 아닌 경우")
    void dateFormat() {
        // given
        ItemCreateRequest request
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022.06.10", "2022.08.31");

        // when
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("날짜 형식은 yyyy-MM-dd 여야 합니다.");
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
