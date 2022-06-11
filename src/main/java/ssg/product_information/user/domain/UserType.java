package ssg.product_information.user.domain;

public enum UserType {
    GENERAL("일반"),
    ENTERPRISE("기업회원");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
