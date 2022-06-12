package ssg.product_information.exception.promotion;

import org.springframework.http.HttpStatus;

import ssg.product_information.exception.ApplicationException;

public class PromotionException extends ApplicationException {

    public PromotionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
