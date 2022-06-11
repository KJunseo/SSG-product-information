package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

import ssg.product_information.exception.ApplicationException;

public class ItemException extends ApplicationException {

    public ItemException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
