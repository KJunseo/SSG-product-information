package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class PromotionItemNumberException extends PromotionException {

    public PromotionItemNumberException() {
        super("프로모션에는 하나 이상의 상품이 포함되어야 합니다.", HttpStatus.BAD_REQUEST);
    }
}
