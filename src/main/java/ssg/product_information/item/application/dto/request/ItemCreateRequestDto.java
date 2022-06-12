package ssg.product_information.item.application.dto.request;

import java.time.LocalDate;

import ssg.product_information.item.domain.ItemType;

public class ItemCreateRequestDto {
    private String name;
    private ItemType type;
    private int price;
    private LocalDate start;
    private LocalDate end;

    public ItemCreateRequestDto(String name, ItemType type, int price, LocalDate start, LocalDate end) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
