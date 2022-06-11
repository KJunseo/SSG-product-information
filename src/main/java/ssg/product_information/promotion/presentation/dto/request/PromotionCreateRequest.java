package ssg.product_information.promotion.presentation.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PromotionCreateRequest {

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    private String name;

    @Min(value = 0, message = "할인 가격은 음수가 될 수 없습니다.")
    private Integer discountAmount;

    @Min(value = 0, message = "할인율은 음수가 될 수 없습니다.")
    private Double discountRate;

    @NotBlank(message = "시작일은 비어있을 수 없습니다.")
    private String startDate;

    @NotBlank(message = "마감일은 비어있을 수 없습니다.")
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
