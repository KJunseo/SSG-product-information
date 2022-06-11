package ssg.product_information.user.presentation.dto;

import ssg.product_information.user.application.dto.request.UserCreateRequestDto;
import ssg.product_information.user.domain.UserStat;
import ssg.product_information.user.domain.UserType;
import ssg.product_information.user.presentation.dto.request.UserCreateRequest;

public class UserAssembler {

    private UserAssembler() {
    }

    public static UserCreateRequestDto userCreateRequestDto(UserCreateRequest request) {
        return new UserCreateRequestDto(
                request.getName(),
                UserType.getType(request.getType()),
                UserStat.getStat(request.getStat())
        );
    }
}
