package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class NoSuchPromotionException extends PromotionException {

    public NoSuchPromotionException() {
        super("존재하지 않는 프로모션입니다.", HttpStatus.BAD_REQUEST);
    }
}
