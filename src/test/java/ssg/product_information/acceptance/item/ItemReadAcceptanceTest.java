package ssg.product_information.acceptance.item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import ssg.product_information.item.presentation.dto.response.ItemPromotionResponse;
import ssg.product_information.item.presentation.dto.response.ItemResponse;
import ssg.product_information.promotion.presentation.dto.request.PromotionCreateRequest;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ssg.product_information.acceptance.item.ItemCreateAcceptanceTest.itemCreate;
import static ssg.product_information.acceptance.promotion.PromotionCreateAcceptanceTest.promotionCreate;
import static ssg.product_information.acceptance.user.UserCreateAcceptanceTest.userCreate;
import static ssg.product_information.acceptance.user.UserDeleteAcceptanceTest.사용자_탈퇴_요청;

@DisplayName("상품 조회 인수테스트")
public class ItemReadAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();

        ItemCreateRequest item1
                = new ItemCreateRequest("새콤달콤", "일반", 500, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        ItemCreateRequest item2
                = new ItemCreateRequest("아이셔", "일반", 1000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        ItemCreateRequest item3
                = new ItemCreateRequest("나이키 운동화", "기업회원상품", 40000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        ItemCreateRequest item4
                = new ItemCreateRequest("와우", "일반", 500, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
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

    @Test
    @DisplayName("전시 중인 상품만 포함되어야 한다.")
    void displayItems() {
        // given
        UserCreateRequest request = new UserCreateRequest("김준서", "일반");
        Long id = userCreate(request);

        LocalDate now = LocalDate.now();
        ItemCreateRequest item
                = new ItemCreateRequest("펩시 제로", "일반", 1100, stringDate(now.minusMonths(5)), stringDate(now.minusMonths(3)));
        itemCreate(item);

        // when
        ExtractableResponse<Response> response = 사용자별_구매_가능_상품_목록_조회_요청(id);
        List<ItemResponse> result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("아이템에 포함된 프로모션 정보를 반환한다. - 정액")
    void promotionOfItemDiscountAmount() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 1000, stringDate(now.minusMonths(1)), stringDate(now.plusMonths(1)), List
                .of(itemId));

        Long promotionId = promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getPromotion().getId()).isEqualTo(promotionId);
    }

    @Test
    @DisplayName("아이템에 포함된 프로모션 정보를 반환한다. - 정률")
    void promotionOfItemDiscountRate() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, stringDate(now.minusMonths(1)), stringDate(now.plusMonths(1)), List
                .of(itemId));

        Long promotionId = promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getPromotion().getId()).isEqualTo(promotionId);
    }

    @Test
    @DisplayName("프로모션이 여러개인 경우 가격이 낮은 프로모션 정보를 반환한다.")
    void minPricePromotion() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        PromotionCreateRequest request1
                = new PromotionCreateRequest("2022 쓱데이", 1000, stringDate(now.minusMonths(1)), stringDate(now.plusMonths(1)), List.of(itemId));
        PromotionCreateRequest request2
                = new PromotionCreateRequest("2021 쓱데이", 0.05, stringDate(now.minusMonths(1)), stringDate(now.plusMonths(1)), List.of(itemId));

        Long promotionId = promotionCreate(request1);
        promotionCreate(request2);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getPromotion().getId()).isEqualTo(promotionId);
    }

    @Test
    @DisplayName("프로모션 적용 가격이 0 이하일시 프로모션 적용이 안된다.")
    void priceUnderZero() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 2000, stringDate(now.minusMonths(1)), stringDate(now.plusMonths(1)), List.of(itemId));

        promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getDiscountPrice()).isNull();
        assertThat(result.getPromotion().getId()).isNull();
    }

    @Test
    @DisplayName("상품에 프로모션이 없는 경우")
    void noPromotionItem() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getDiscountPrice()).isNull();
        assertThat(result.getPromotion().getId()).isNull();
    }

    @Test
    @DisplayName("현재 시점을 기준으로 진행중인 프로모션만 적용되어야 한다.")
    void promotionPeriod() {
        // given
        LocalDate now = LocalDate.now();

        ItemCreateRequest item
                = new ItemCreateRequest("게토레이", "일반", 2000, stringDate(now.minusMonths(3)), stringDate(now.plusMonths(3)));
        Long itemId = itemCreate(item);

        PromotionCreateRequest request
                = new PromotionCreateRequest("쓱데이", 0.05, stringDate(now.plusMonths(1)), stringDate(now.plusMonths(2)), List.of(itemId));

        promotionCreate(request);

        // when
        ExtractableResponse<Response> response = 상품에_존재하는_프로모션_정보_요청(itemId);
        ItemPromotionResponse result = response.as(new TypeRef<>() {});

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getId()).isEqualTo(itemId);
        assertThat(result.getDiscountPrice()).isNull();
        assertThat(result.getPromotion().getId()).isNull();
    }

    private String stringDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
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

    public ExtractableResponse<Response> 상품에_존재하는_프로모션_정보_요청(Long id) {
        return RestAssured.given(spec)
                          .log().all()
                          .contentType(APPLICATION_JSON_VALUE)
                          .when()
                          .get("/items/" + id + "/promotions")
                          .then()
                          .log().all()
                          .extract();
    }
}
