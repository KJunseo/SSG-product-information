package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

import ssg.product_information.exception.ApplicationException;

public class UserException extends ApplicationException {

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
