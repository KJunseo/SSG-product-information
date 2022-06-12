package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

public class NoSuchUserException extends UserException {

    public NoSuchUserException() {
        super("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
    }
}
