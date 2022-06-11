package ssg.product_information.promotion.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import ssg.product_information.exception.promotion.ViolateDiscountPolicyException;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String promotionName;

    private Integer discountAmount;

    private Double discountRate;

    private LocalDate promotionStartDate;

    private LocalDate promotionEndDate;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionItem> promotionItems = new ArrayList<>();

    protected Promotion() {
    }

    public Promotion(
            String promotionName,
            Integer discountAmount,
            Double discountRate,
            LocalDate promotionStartDate,
            LocalDate promotionEndDate
    ) {
        validatesDiscountPolicy(discountAmount, discountRate);
        this.promotionName = promotionName;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
    }

    private void validatesDiscountPolicy(Integer discountAmount, Double discountRate) {
        if (!Objects.isNull(discountAmount) && !Objects.isNull(discountRate)) {
            throw new ViolateDiscountPolicyException();
        }
    }
}
