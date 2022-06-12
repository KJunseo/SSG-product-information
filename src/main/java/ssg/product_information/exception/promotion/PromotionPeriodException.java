package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class PromotionPeriodException extends PromotionException {

    public PromotionPeriodException() {
        super("마감일은 시작일 이전일 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
