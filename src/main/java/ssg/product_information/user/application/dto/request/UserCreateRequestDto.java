package ssg.product_information.user.application.dto.request;

import ssg.product_information.user.domain.UserType;

public class UserCreateRequestDto {
    private String name;
    private UserType type;

    public UserCreateRequestDto(String name, UserType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }
}
