package ssg.product_information.item.presentation.dto.request;

public class ItemCreateRequest {
    private String name;
    private String type;
    private int price;
    private String start;
    private String end;

    public ItemCreateRequest() {
    }

    public ItemCreateRequest(String name, String type, int price, String start, String end) {
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

    public int getPrice() {
        return price;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
