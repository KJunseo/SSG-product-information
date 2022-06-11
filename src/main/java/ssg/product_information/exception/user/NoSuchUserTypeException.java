package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

public class NoSuchUserTypeException extends UserException {

    public NoSuchUserTypeException() {
        super("존재하지 않는 유저 타입입니다.", HttpStatus.BAD_REQUEST);
    }
}
