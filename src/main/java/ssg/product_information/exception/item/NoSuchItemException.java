package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

public class NoSuchItemException extends ItemException {

    public NoSuchItemException() {
        super("존재하지 않는 상품입니다.", HttpStatus.BAD_REQUEST);
    }
}
