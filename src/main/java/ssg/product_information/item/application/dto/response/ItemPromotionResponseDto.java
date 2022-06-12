package ssg.product_information.item.application.dto.response;

import java.time.LocalDate;

import ssg.product_information.promotion.application.dto.response.PromotionResponseDto;

public class ItemPromotionResponseDto {
    private Long id;
    private String name;
    private String type;
    private Integer originalPrice;
    private Integer discountPrice;
    private LocalDate displayStartDate;
    private LocalDate displayEndDate;
    private PromotionResponseDto promotion;

    public ItemPromotionResponseDto(
            Long id,
            String name,
            String type,
            Integer originalPrice,
            Integer discountPrice,
            LocalDate displayStartDate,
            LocalDate displayEndDate,
            PromotionResponseDto promotion
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.displayStartDate = displayStartDate;
        this.displayEndDate = displayEndDate;
        this.promotion = promotion;
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

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public LocalDate getDisplayStartDate() {
        return displayStartDate;
    }

    public LocalDate getDisplayEndDate() {
        return displayEndDate;
    }

    public PromotionResponseDto getPromotion() {
        return promotion;
    }
}
