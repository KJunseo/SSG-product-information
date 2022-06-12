package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class PromotionItemDisplayPeriodException extends PromotionException {

    public PromotionItemDisplayPeriodException() {
        super("프로모션에 포함된 상품의 전시 기간이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
