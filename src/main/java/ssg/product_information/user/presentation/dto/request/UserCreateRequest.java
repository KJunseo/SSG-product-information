package ssg.product_information.user.presentation.dto.request;

public class UserCreateRequest {
    private String name;
    private String type;
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
