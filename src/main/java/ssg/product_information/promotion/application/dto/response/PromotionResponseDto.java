package ssg.product_information.promotion.application.dto.response;

import java.time.LocalDate;

public class PromotionResponseDto {
    private Long id;
    private String name;
    private Integer discountAmount;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public PromotionResponseDto() {
    }

    public PromotionResponseDto(
            Long id,
            String name,
            Integer discountAmount,
            Double discountRate,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
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
}
