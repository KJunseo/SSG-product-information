package ssg.product_information.user.presentation.dto.request;

import javax.validation.constraints.NotBlank;

public class UserCreateRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank(message = "타입은 비어있을 수 없습니다.")
    private String type;

    @NotBlank(message = "상태는 비어있을 수 없습니다.")
    private String stat;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String name, String type, String stat) {
        this.name = name;
        this.type = type;
        this.stat = stat;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStat() {
        return stat;
    }
}
