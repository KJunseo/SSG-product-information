package ssg.product_information.promotion.application.dto.request;

import java.time.LocalDate;
import java.util.List;

public class PromotionCreateRequestDto {
    private String name;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> products;

    public PromotionCreateRequestDto(
            String name,
            Integer discountAmount,
            Double discountRate,
            LocalDate startDate,
            LocalDate endDate,
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Long> getProducts() {
        return products;
    }
}
