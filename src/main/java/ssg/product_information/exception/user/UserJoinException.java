package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

public class UserJoinException extends UserException {

    public UserJoinException() {
        super("탈퇴 상태로 가입할 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
