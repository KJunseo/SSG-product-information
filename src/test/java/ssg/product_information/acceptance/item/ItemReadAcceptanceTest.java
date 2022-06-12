package ssg.product_information.acceptance.item;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import ssg.product_information.acceptance.AcceptanceTest;
import ssg.product_information.exception.dto.ExceptionResponse;
import ssg.product_information.item.presentation.dto.request.ItemCreateRequest;
import ssg.product_information.item.presentation.dto.response.ItemResponse;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ssg.product_information.acceptance.item.ItemCreateAcceptanceTest.itemCreate;
import static ssg.product_information.acceptance.user.UserCreateAcceptanceTest.userCreate;
import static ssg.product_information.acceptance.user.UserDeleteAcceptanceTest.사용자_탈퇴_요청;

@DisplayName("상품 조회 인수테스트")
public class ItemReadAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-10", "2022-06-20");
        ItemCreateRequest item2
                = new ItemCreateRequest("새콤달콤", "일반", 1000, "2022-06-10", "2022-07-15");
        ItemCreateRequest item3
                = new ItemCreateRequest("나이키 운동화", "기업회원상품", 40000, "2022-06-25", "2022-08-31");
        ItemCreateRequest item4
                = new ItemCreateRequest("새콤달콤", "일반", 500, "2022-06-26", "2022-09-30");
        itemCreate(item1);
        itemCreate(item2);
        itemCreate(item3);
        itemCreate(item4);
    }

    @Test
    @DisplayName("사용자가 구매할 수 있는 상품 정보를 반환한다. - 일반 회원")
    void itemsByGeneralUser() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반");
        Long id = userCreate(request);

        // when
        ExtractableResponse<Response> response = 사용자별_구매_가능_상품_목록_조회_요청(id);
        List<ItemResponse> result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("사용자가 구매할 수 있는 상품 정보를 반환한다. - 기업 회원")
    void itemsByEnterpriseUser() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "기업회원");
        Long id = userCreate(request);

        // when
        ExtractableResponse<Response> response = 사용자별_구매_가능_상품_목록_조회_요청(id);
        List<ItemResponse> result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(4);
    }

    @Test
    @DisplayName("탈퇴한 회원이라면 조회할 수 없다.")
    void withdrawalUser() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "기업회원");
        Long id = userCreate(request);
        사용자_탈퇴_요청(id);

        // when
        ExtractableResponse<Response> response = 사용자별_구매_가능_상품_목록_조회_요청(id);
        ExceptionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getMessage()).isEqualTo("존재하지 않는 유저입니다.");
    }

    public ExtractableResponse<Response> 사용자별_구매_가능_상품_목록_조회_요청(Long id) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/items/purchase?userId=" + id)
                          .then()
                          .log().all()
                          .extract();
    }
}
