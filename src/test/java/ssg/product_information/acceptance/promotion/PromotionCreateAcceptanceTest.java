package ssg.product_information.acceptance.promotion;

import java.util.Collections;
import java.util.List;

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
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ssg.product_information.acceptance.item.ItemCreateAcceptanceTest.itemCreate;

@DisplayName("프로모션 생성 인수테스트")
public class PromotionCreateAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("새로운 프로모션 생성 - 할인 가격")
    void createPromotionDiscountAmount() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 1000, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    @Test
    @DisplayName("새로운 프로모션 생성 - 할인율")
    void createPromotionDiscountRate() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름은 비어있을 수 없다.")
    void emptyName(String name) {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest(name, 0.05, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("프로모션 시작일은 비어있을 수 없다.")
    void emptyStartDate(String date) {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, date, "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("시작일은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("프로모션 마감일은 비어있을 수 없다.")
    void emptyEndDate(String date) {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-06-10", date, itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("마감일은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("할인금액이나 할인율 둘 중 하나는 존재해야한다.")
    void violateDiscountPolicy() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", null, null, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("할인 가격과 할인율은 둘 중 하나만 존재할 수 있습니다.");
    }

    @Test
    @DisplayName("할인금액은 음수일 수 없다.")
    void negativeDiscountAmount() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", -100, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("할인 가격은 음수가 될 수 없습니다.");
    }

    @Test
    @DisplayName("할인율은 음수일 수 없다.")
    void negativeDiscountRate() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", -0.05, "2022-06-10", "2022-08-31", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("할인율은 음수가 될 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션 마감일은 프로모션 시작일 이전일 수 없다.")
    void wrongPeriod() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-31");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-06-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-07-10", "2022-06-25", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("마감일은 시작일 이전일 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션에 포함된 상품이 없는 경우")
    void noItem() {
        // given
        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-06-10", "2022-07-25", Collections.emptyList());

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("프로모션에는 하나 이상의 상품이 포함되어야 합니다.");
    }

    @Test
    @DisplayName("프로모션에 포함된 상품 중 하나라도 프로모션 기간에 전시되지 않는다면 프로모션을 생성할 수 없다.")
    void invalidPeriodItemInPromotion() {
        // given
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-20");
        ItemCreateRequest item2
                = new ItemCreateRequest("무뚝뚝감자칩", "일반", 1500, "2022-05-10", "2022-08-31");
        List<Long> itemIds = List.of(itemCreate(item1), itemCreate(item2));

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-06-01", "2022-06-30", itemIds);

        // when
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("프로모션에 포함된 상품의 전시 기간이 올바르지 않습니다.");
    }

    public static Long promotionCreate(PromotionCreateRequest request) {
        ExtractableResponse<Response> response = 새로운_프로모션_정보_등록_요청(request);
        String location = response.header("Location");
        return Long.parseLong(location.substring(location.length() - 1));
    }

    public static ExtractableResponse<Response> 새로운_프로모션_정보_등록_요청(PromotionCreateRequest request) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .body(request)
                          .when()
                          .post("/promotions")
                          .then()
                          .log().all()
                          .extract();
    }
}
