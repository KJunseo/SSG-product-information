package ssg.product_information.user.domain;

import java.util.Arrays;

import ssg.product_information.exception.user.NoSuchUserStatException;

public enum UserStat {
    NORMAL("정상"),
    WITHDRAW("탈퇴");

    private final String stat;

    UserStat(String stat) {
        this.stat = stat;
    }

    public static UserStat getStat(String stat) {
        return Arrays.stream(UserStat.values())
                     .filter(s -> s.stat.equals(stat))
                     .findFirst()
                     .orElseThrow(NoSuchUserStatException::new);
    }
}
