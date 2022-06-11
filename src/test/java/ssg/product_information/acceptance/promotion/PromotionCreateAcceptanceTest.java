package ssg.product_information.acceptance.promotion;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ssg.product_information.acceptance.item.ItemCreateAcceptanceTest.새로운_상품_정보_등록_요청;

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

    private Long itemCreate(ItemCreateRequest item) {
        ExtractableResponse<Response> response = 새로운_상품_정보_등록_요청(item);
        String location = response.header("Location");
        return Long.parseLong(location.substring(location.length() - 1));
    }

    private ExtractableResponse<Response> 새로운_프로모션_정보_등록_요청(PromotionCreateRequest request) {
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
