package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

public class NoSuchUserStatException extends UserException {

    public NoSuchUserStatException() {
        super("존재하지 않는 유저 상태입니다.", HttpStatus.BAD_REQUEST);
    }
}
