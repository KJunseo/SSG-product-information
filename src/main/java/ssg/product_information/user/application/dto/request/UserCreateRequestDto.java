package ssg.product_information.user.application.dto.request;

import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;

public class UserCreateRequestDto {
    private String name;
    private UserType type;
    private UserStat stat;

    public UserCreateRequestDto(String name, UserType type, UserStat stat) {
        this.name = name;
        this.type = type;
        this.stat = stat;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }

    public UserStat getStat() {
        return stat;
    }
}
