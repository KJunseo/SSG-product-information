package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

public class ItemDisplayPeriodException extends ItemException {

    public ItemDisplayPeriodException() {
        super("마감일은 시작일 이전일 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
