package ssg.product_information.promotion.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.*;

import ssg.product_information.exception.promotion.PromotionDisplayPeriodException;
import ssg.product_information.exception.promotion.PromotionItemNumberException;
import ssg.product_information.exception.promotion.ViolateDiscountPolicyException;
import ssg.product_information.item.domain.Item;

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

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.PERSIST)
    private List<PromotionItem> promotionItems = new ArrayList<>();

    protected Promotion() {
    }

    public Promotion(String promotionName, Integer discountAmount, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        this(promotionName, discountAmount, null, promotionStartDate, promotionEndDate);
    }

    public Promotion(String promotionName, Double discountRate, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        this(promotionName, null, discountRate, promotionStartDate, promotionEndDate);
    }

    public Promotion(
            String promotionName,
            Integer discountAmount,
            Double discountRate,
            LocalDate promotionStartDate,
            LocalDate promotionEndDate
    ) {
        validates(discountAmount, discountRate, promotionStartDate, promotionEndDate);
        this.promotionName = promotionName;
        if (!Objects.isNull(discountAmount)) {
            this.discountAmount = discountAmount;
        }
        if (!Objects.isNull(discountRate)) {
            this.discountRate = discountRate;
        }
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
    }

    private void validates(Integer discountAmount, Double discountRate, LocalDate startDate, LocalDate endDate) {
        validatesDiscountPolicy(discountAmount, discountRate);
        validatesDisplayPeriod(startDate, endDate);
    }

    private void validatesDiscountPolicy(Integer discountAmount, Double discountRate) {
        if (!Objects.isNull(discountAmount) && !Objects.isNull(discountRate)) {
            throw new ViolateDiscountPolicyException();
        }
        if (Objects.isNull(discountAmount) && Objects.isNull(discountRate)) {
            throw new ViolateDiscountPolicyException();
        }
    }

    private void validatesDisplayPeriod(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new PromotionDisplayPeriodException();
        }
    }

    public void addItems(List<Item> items) {
        if (items.isEmpty()) {
            throw new PromotionItemNumberException();
        }
        List<PromotionItem> promotionItems = items.stream()
                                                  .map(item -> new PromotionItem(this, item))
                                                  .collect(Collectors.toList());
        this.promotionItems.addAll(promotionItems);
    }

    public Long getId() {
        return id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public LocalDate getPromotionStartDate() {
        return promotionStartDate;
    }

    public LocalDate getPromotionEndDate() {
        return promotionEndDate;
    }

    public List<PromotionItem> getPromotionItems() {
        return promotionItems;
    }
}
