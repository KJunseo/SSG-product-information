package ssg.product_information.item.application.dto.response;

import java.time.LocalDate;

import ssg.product_information.item.domain.ItemType;

public class ItemResponseDto {
    private Long id;
    private String name;
    private ItemType type;
    private Integer price;
    private LocalDate displayStartDate;
    private LocalDate displayEndDate;

    public ItemResponseDto(Long id, String name, ItemType type, Integer price, LocalDate displayStartDate, LocalDate displayEndDate) {
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

    public ItemType getType() {
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
