package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

public class NoSuchItemTypeException extends ItemException {

    public NoSuchItemTypeException() {
        super("존재하지 않는 상품 타입입니다.", HttpStatus.BAD_REQUEST);
    }
}
