package ssg.product_information.exception.item;

import org.springframework.http.HttpStatus;

import ssg.product_information.exception.ApplicationException;

public class DateFormatException extends ApplicationException {

    public DateFormatException() {
        super("날짜 형식은 yyyy-MM-dd 여야 합니다.", HttpStatus.BAD_REQUEST);
    }
}
