package ssg.product_information.promotion.presentation.dto.request;

import java.util.List;

public class PromotionCreateRequest {
    private String name;
    private Integer discountAmount;
    private Double discountRate;
    private String startDate;
    private String endDate;
    private List<Long> products;

    public PromotionCreateRequest() {
    }

    public PromotionCreateRequest(
            String name, Integer discountAmount, String startDate, String endDate, List<Long> products
    ) {
        this(name, discountAmount, null, startDate, endDate, products);
    }

    public PromotionCreateRequest(
            String name, Double discountRate, String startDate, String endDate, List<Long> products
    ) {
        this(name, null, discountRate, startDate, endDate, products);
    }

    public PromotionCreateRequest(
            String name,
            Integer discountAmount,
            Double discountRate,
            String startDate,
            String endDate,
            List<Long> products
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<Long> getProducts() {
        return products;
    }
}
