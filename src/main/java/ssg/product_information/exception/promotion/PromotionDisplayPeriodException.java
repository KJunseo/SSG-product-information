package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

public class PromotionDisplayPeriodException extends PromotionException {

    public PromotionDisplayPeriodException() {
        super("마감일은 시작일 이전일 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
