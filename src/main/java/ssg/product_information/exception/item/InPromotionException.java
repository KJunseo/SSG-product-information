package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

public class InPromotionException extends ItemException {

    public InPromotionException() {
        super("프로모션 진행 중인 상품은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
