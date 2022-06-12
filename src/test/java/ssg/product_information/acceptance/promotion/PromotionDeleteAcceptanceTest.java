package ssg.product_information.acceptance.promotion;

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

@DisplayName("프로모션 삭제 인수 테스트")
public class PromotionDeleteAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("프로모션을 삭제할 수 있다.")
    void delete() {
        // given
        ItemCreateRequest item
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-09-20");
        Long itemId = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 1000, "2022-06-10", "2022-08-31", List.of(itemId));
        Long id = promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 프로모션_삭제_요청(id);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("존재하지 않는 프로모션은 삭제할 수 없다.")
    void noExist() {
        // given
        Long id = 100L;

        // when
        ExtractableResponse<Response> response = 프로모션_삭제_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 프로모션입니다.");
    }

    public ExtractableResponse<Response> 프로모션_삭제_요청(Long id) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .delete("/promotions/" + id)
                          .then()
                          .log().all()
                          .extract();
    }
}
