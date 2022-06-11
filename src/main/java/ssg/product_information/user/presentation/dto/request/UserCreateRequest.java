package ssg.product_information.user.presentation.dto.request;

import javax.validation.constraints.NotBlank;

public class UserCreateRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank(message = "타입은 비어있을 수 없습니다.")
    private String type;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
