package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class ViolateDiscountPolicyException extends PromotionException {

    public ViolateDiscountPolicyException() {
        super("할인 가격과 할인율은 둘 중 하나만 존재할 수 있습니다.", HttpStatus.BAD_REQUEST);
    }
}
