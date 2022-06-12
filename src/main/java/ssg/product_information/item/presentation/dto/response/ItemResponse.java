package ssg.product_information.item.presentation.dto.response;

import java.time.LocalDate;

public class ItemResponse {
    private Long id;
    private String name;
    private String type;
    private Integer price;
    private LocalDate displayStartDate;
    private LocalDate displayEndDate;

    public ItemResponse() {
    }

    public ItemResponse(Long id, String name, String type, Integer price, LocalDate displayStartDate, LocalDate displayEndDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.displayStartDate = displayStartDate;
        this.displayEndDate = displayEndDate;
    }

    public Long getId() {
        return id;
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

    public LocalDate getDisplayStartDate() {
        return displayStartDate;
    }

    public LocalDate getDisplayEndDate() {
        return displayEndDate;
    }
}
