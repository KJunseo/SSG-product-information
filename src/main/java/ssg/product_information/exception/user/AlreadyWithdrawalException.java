package ssg.product_information.exception.user;

import org.springframework.http.HttpStatus;

public class AlreadyWithdrawalException extends UserException {

    public AlreadyWithdrawalException() {
        super("이미 탈퇴한 사용자입니다.", HttpStatus.BAD_REQUEST);
    }
}
