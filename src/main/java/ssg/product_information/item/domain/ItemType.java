package ssg.product_information.item.domain;

import java.util.Arrays;

import ssg.product_information.exception.item.NoSuchItemTypeException;

public enum ItemType {
    GENERAL_MEMBERSHIP("일반"),
    CORPORATE_MEMBERSHIP("기업회원상품");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public static ItemType getType(String type) {
        return Arrays.stream(ItemType.values())
                     .filter(t -> t.type.equals(type))
                     .findFirst()
                     .orElseThrow(NoSuchItemTypeException::new);
    }
}
