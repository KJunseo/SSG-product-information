package ssg.product_information.item.presentation.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ItemCreateRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank(message = "타입은 비어있을 수 없습니다.")
    private String type;

    @NotNull(message = "가격은 비어있을 수 없습니다.")
    private Integer price;

    @NotBlank(message = "시작일은 비어있을 수 없습니다.")
    private String start;

    @NotBlank(message = "마감일은 비어있을 수 없습니다.")
    private String end;

    public ItemCreateRequest() {
    }

    public ItemCreateRequest(String name, String type, Integer price, String start, String end) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
