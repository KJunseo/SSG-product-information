package ssg.product_information.acceptance.item;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
import static ssg.product_information.acceptance.promotion.PromotionCreateAcceptanceTest.promotionCreate;

@DisplayName("상품 삭제 인수 테스트")
public class ItemDeleteAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("상품을 삭제할 수 있다.")
    void delete() {
        // given
        ItemCreateRequest item
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-08-20");
        Long id = itemCreate(item);

        // when
        ExtractableResponse<Response> response = 상품_삭제_요청(id);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제할 수 없다.")
    void notExist() {
        // given
        Long id = 100L;

        // when
        ExtractableResponse<Response> response = 상품_삭제_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("프로모션 진행 중인 상품은 삭제할 수 없다.")
    void promotionItem() {
        // given
        ItemCreateRequest item
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-05-10", "2022-08-20");
        Long id = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, "2022-06-01", "2022-06-30", List.of(id));
        promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 상품_삭제_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("프로모션 진행 중인 상품은 삭제할 수 없습니다.");
    }

    public ExtractableResponse<Response> 상품_삭제_요청(Long id) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .delete("/items/" + id)
                          .then()
                          .log().all()
                          .extract();
    }
}
