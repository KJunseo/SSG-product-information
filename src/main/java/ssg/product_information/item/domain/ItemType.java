package ssg.product_information.item.domain;

public enum ItemType {
    GENERAL_MEMBERSHIP("일반"),
    CORPORATE_MEMBERSHIP("기업회원상품");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }
}
