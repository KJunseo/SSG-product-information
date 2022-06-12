package ssg.product_information.user.domain;

import java.util.Arrays;

import ssg.product_information.exception.user.NoSuchUserTypeException;

public enum UserType {
    GENERAL("일반"),
    ENTERPRISE("기업회원");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public static UserType getType(String type) {
        return Arrays.stream(UserType.values())
                     .filter(t -> t.type.equals(type))
                     .findFirst()
                     .orElseThrow(NoSuchUserTypeException::new);
    }

    public String getType() {
        return type;
    }
}
